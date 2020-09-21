package com.zbf.user.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.uploadstarter.UploadService;
import com.sjk.utils.RandomUtil;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.entity.xsl.ExClass;
import com.zbf.common.utils.AllStatusEnum;
import com.zbf.common.utils.Md5;
import com.zbf.common.utils.PoiUtil;
import com.zbf.user.service.IBaseUserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.ITestOrConfiguration;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author thyu
 * @since 2020-09-11
 */
@RestController
@RequestMapping("/baseUser")
public class BaseUserControllers {

    private static String url = "http://120.55.44.68:9000";
    private static String access = "minioadmin";
    private static String secret = "minioadmin";
    private static String bucket = "three";

    @Autowired
    private IBaseUserService iBaseUserService;

    @Autowired
    private UploadService uploadService;



    /**
     * @Author 申嘉坤
     * @Description //TODO * @param current
    * @param size
    * @param vo
     * @Date 9:03 2020/9/18
     * @Param 
     * @return 用户列表
     **/
    @RequestMapping("/getUserList")
    public ResponseResult list(@RequestParam(defaultValue = "1") long current, @RequestParam(defaultValue = "3") long size, BaseUser vo) {

        ResponseResult responseResult = new ResponseResult();
        Page<Object> page = new Page<>(current, size);
        IPage<BaseUser> pageInfo = iBaseUserService.selectPageVo(page, vo);

        if (pageInfo.getRecords()!=null){
            responseResult.setCode(AllStatusEnum.REQUEST_SUCCESS.getCode());
            responseResult.setMsg(AllStatusEnum.REQUEST_SUCCESS.getMsg());
            responseResult.setResult(pageInfo);
        }else {
            responseResult.setCode(AllStatusEnum.REQUEST_FAIRLE.getCode());
            responseResult.setMsg(AllStatusEnum.REQUEST_FAIRLE.getMsg());
        }

        return responseResult;
    }


    /**
     * @Author 申嘉坤
     * @Description //TODO * @param vo
     * @Date 8:49 2020/9/20
     * @Param 
     * @return 添加用户上
     **/
    @RequestMapping("/addUser")
    public ResponseResult add(@RequestBody BaseUser vo) {
        System.out.println(vo.getHeadimg());
        ResponseResult responseResult = new ResponseResult();
        String s = RandomUtil.randomNumber(4);
        String encodePass= Md5.encode(vo.getPassWord()+s, "MD5");
        BaseUser user = new BaseUser(vo.getId(),
                vo.getUserName(),
                vo.getLoginName(),
                encodePass,
                vo.getTel(),
                vo.getSex(),
                vo.getEmail(),
                s);
        user.setId(Long.valueOf(time()));
        user.setCreateTime(new Date());
        user.setStatus(0);
        user.setHeadimg(vo.getHeadimg());
        boolean save = iBaseUserService.saveOrUpdate(user);
        if(save){
            responseResult.setCode(AllStatusEnum.REQUEST_SUCCESS.getCode());
            responseResult.setSuccess(AllStatusEnum.REQUEST_SUCCESS.getMsg());
        }else {
            responseResult.setCode(AllStatusEnum.REQUEST_FAIRLE.getCode());
            responseResult.setSuccess(AllStatusEnum.REQUEST_FAIRLE.getMsg());
        }
        return responseResult;
    }

    @RequestMapping("/updUser")
    public boolean upd(@RequestBody BaseUser baseUser){
        boolean b = iBaseUserService.updateById(baseUser);
        return b;
    }

    /**
     * @Author 申嘉坤
     * @Description //TODO * @param
     * @Date 8:28 2020/9/18
     * @Param
     * @return 菜单添加根据当前时间
     **/
    public String time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        String response = format.replaceAll("[[\\s-:punct:]]","");
        return response;
    }
    /**
     * @Author 申嘉坤
     * @Description //TODO * @param null
     * @Date 14:03 2020/9/21
     * @Param 
     * @return 删除用户
     **/
    @RequestMapping("/deleteUser")
    public boolean delete(Integer id) {
        boolean b = iBaseUserService.removeById(id);
        return b;
    }
    
    /**
     * @Author 申嘉坤
     * @Description //TODO * @param null
     * @Date 15:27 2020/9/20
     * @Param 
     * @return 状态停用或启动
     **/
    @RequestMapping("/getStatus")
    public void getStatus(@RequestBody BaseUser baseUser){
        if (baseUser.getStatus()==1){
            baseUser.setStatus(0);
        }else {
            baseUser.setStatus(1);
        }
        iBaseUserService.updateById(baseUser);
    }
    
    /**
     * @Author 申嘉坤
     * @Description //TODO * @param null
     * @Date 8:50 2020/9/20
     * @Param 
     * @return 导出word
     **/
    @RequestMapping("/getDoc")
    public boolean getDoc(@RequestBody BaseUser user) throws IOException, TemplateException {
        try {
            Map<String,String> dataMap = new HashMap<String,String>();
            if(user.getId() != null ){
                dataMap.put("id", String.valueOf(user.getId()));
            }else{
                dataMap.put("id", "该用户没有id");
            }
            if(user.getUserName() != null){
                dataMap.put("userName", user.getUserName());
            }else{
                dataMap.put("userName","该用户没有姓名");
            }
            if(user.getLoginName() != null){
                dataMap.put("loginName", user.getLoginName());
            }else{
                dataMap.put("loginName","该用户没有登录姓名");
            }
            if(user.getTel() != null ){
                dataMap.put("tel", user.getTel());
            }else{
                dataMap.put("tel","该用户没有手机号");
            }
            if(user.getRname() != null){
                dataMap.put("rname", user.getRname());
            }else{
                dataMap.put("rname","该用户没有所属角色");
            }if(user.getHeadimg()!=null){
                String s = ImageToBase64ByOnline(user.getHeadimg());
                s = s.substring(s.indexOf(',')+1);
                dataMap.put("headimg",s);
            }else {
                dataMap.put("headimg","该用户没有图片");
            }

            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            //指定模板路径的第二种方式,我的路径是D:/      还有其他方式
            configuration.setDirectoryForTemplateLoading(new File("D:/Doc"));


            // 输出文档路径及名称
            File outFile = new File("D:/Doc/file/"+user.getUserName()+".doc");
            //以utf-8的编码读取ftl文件
            Template t =  configuration.getTemplate("word.ftl","utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
            t.process(dataMap, out);
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    public static String ImageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }


    /**
         * @Author 申嘉坤
         * @Description //TODO * @param null
         * @Date 9:14 2020/9/20
         * @Param
         * @return miniu上传图片
         **/


    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException, InvalidPortException, InvalidEndpointException {
        String fileName = null;
        try {
            //创建MinioClient对象
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(url)
                            .credentials(access, secret)
                            .build();
            InputStream in = file.getInputStream();
            fileName = file.getOriginalFilename();
            //String fileName = file.getName();
            //文件上传到minio上的Name把文件后缀带上，不然下载出现格式问题
            fileName = UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
            //创建头部信息
            Map<String, String> headers = new HashMap<>(10);
            //添加自定义内容类型
            headers.put("Content-Type", "image/jpeg");
            //添加存储类
            headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
            //添加自定义/用户元数据
            Map<String, String> userMetadata = new HashMap<>(10);
            userMetadata.put("My-Project", "Project One");
            //上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(fileName).stream(
                            in, in.available(), -1)
                            .headers(headers)
                            .build());
            in.close();
            System.out.println(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println(url + "/" + bucket + "/" + fileName);
        return url + "/" + bucket + "/" + fileName;


    }
    
    /**
     * @Author 申嘉坤
     * @Description //TODO * @param null
     * @Date 20:06 2020/9/20
     * @Param 
     * @return xls导出
     **/

    @RequestMapping("toExcel")
    public ResponseResult toExcel(@RequestBody ExClass excleEntity){
        System.err.println(excleEntity);
        ResponseResult responseResult = ResponseResult.getResponseResult();
        try {
            //通过数据源获取与数据库的连接  没配置spring的可用原生的jdbc来获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mangner?serverTimezone=UTC", "root", "root");
            //查询的sql语句

            //拼接需要查询的字段
            StringBuffer stringBufferSelect = new StringBuffer();
            String types = "";
            if(excleEntity.getType()!=null && excleEntity.getType().length>0){
                types = "";
                for (String type:excleEntity.getType()) {
                    types += type+",";
                }
                stringBufferSelect.append(types.substring(0,types.length()-1));
            }else{
                stringBufferSelect.append("u.*");
            }

            //拼接需要根据什么排序
            StringBuffer stringBufferOrderType = new StringBuffer();
            String orders = "";
            if(excleEntity.getOrderType()!=null && excleEntity.getOrderType().length>0){
                stringBufferOrderType.append("ORDER BY ");
                orders = "";
                if(excleEntity.getOrderType().length>1){
                    for (String order:excleEntity.getOrderType()) {
                        orders += order+" and ";
                    }
                    stringBufferOrderType.append(orders.substring(0,orders.length()-4));
                }else{
                    String[] orderType = excleEntity.getOrderType();
                    stringBufferOrderType.append(orderType[0]);
                }
            }else{
                stringBufferOrderType.append(" ");
            }

            //拼接需要根据什么排序
            StringBuffer stringBufferOrder = new StringBuffer();
            //拼接需要升序还是降序
            if(excleEntity.getOrder()!=null && !("").equals(excleEntity.getOrder())){
                stringBufferOrder.append(" "+excleEntity.getOrder());
            }else{
                stringBufferOrder.append(" ");
            }

            //拼接要导出多少条数据
            StringBuffer stringBufferLimit = new StringBuffer();
            if(excleEntity.getTotal()!=null && excleEntity.getTotal()!=0){
                stringBufferLimit.append(" LIMIT 0,"+excleEntity.getTotal());
            }else{
                stringBufferOrder.append(" ");
            }

            StringBuilder sql=new StringBuilder("select "+stringBufferSelect+" from base_user u LEFT JOIN base_user_role ur on u.id = ur.userId" +
                    " LEFT JOIN base_role r on r.id = ur.roleId GROUP BY u.id "+stringBufferOrderType+stringBufferOrder+stringBufferLimit);

            System.err.println("打印sql=="+sql);
            //生成的excel表的路径   注意文件名要和数据库中表的名称一致  因为导入时要用到。
            String filePath="D:\\Excel\\"+excleEntity.getExcleName()+".xls";
            //导出
            PoiUtil.exportToExcel(connection,sql.toString(), "base_user", filePath);
            responseResult.setCode(200);
            responseResult.setSuccess("导出excel成功");
            return responseResult;
        }catch (Exception x){
            x.printStackTrace();
            responseResult.setCode(500);
            responseResult.setSuccess("导出excel失败");
            return responseResult;
        }

    }

}



