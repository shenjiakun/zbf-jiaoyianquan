package com.zbf.user.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;*/
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *Excl导入
 * @author: chengshoufei
 * @Date: 2020/9/21 18:06
 */
public class ParseExcelToJSONArrayUtil {



    /**
     * description: 解析excel文件成JSONArray
     *
     * @param file
     * @param excelTemplateRowNum 对应excel模板内容总行数
     * @param jsonRowNum 以excel模板某行作为JSON对象键
     *
     * @return JSONArray
     */
    public JSONArray parseExcelFile(MultipartFile file, int excelTemplateRowNum, int jsonRowNum) throws Exception{

        JSONArray array = new JSONArray();
        System.out.println(file.getOriginalFilename());
        File newFile = multipartFileToFile(file);
        System.out.println(newFile);
        array = readXLSOrXLSX(newFile, excelTemplateRowNum, jsonRowNum);
        System.out.println(array);

        //删除在项目目录下自动生成一个临时的file文件
        File del = new File(newFile.toURI());
        del.delete();

        return array;
    }

    /**
     * MultipartFile转换成能解析的File文件
     *
     * @param multipartFile
     * @return File
     */
    public File multipartFileToFile (MultipartFile multipartFile) throws Exception{
        File f = null;
        if(multipartFile.equals("")||multipartFile.getSize() <= 0) multipartFile = null;
        else{
            InputStream ins = multipartFile.getInputStream();
            f = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, f);
        }
        return f;
    }

    /**
     * 流转换成File文件
     *
     * @param file
     * @param inputStream
     */
    public void inputStreamToFile(InputStream inputStream, File file) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1){
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常："+e.getMessage());
        }finally {
            try {
                if (os != null){
                    os.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭io产生异常："+e.getMessage());
            }
        }
    }

    /**
     * 读取XLS或者XLSX文件
     * @param
     * @return
     * @throws Exception
     */
    public JSONArray readXLSOrXLSX(File file, int excelTemplateRowNum, int jsonRowNum) throws Exception {
        Workbook book = null;
        try {
            book = new XSSFWorkbook(file);
        } catch (Exception ex) {
            book = new HSSFWorkbook(new FileInputStream(file));
        }
        Sheet sheet = book.getSheetAt(0);
        return read(sheet, book, excelTemplateRowNum, jsonRowNum);
    }

    /**
     * 解析数据
     * @param sheet 表格sheet对象
     * @param book 用于流关闭
     * @return
     * @throws IOException
     */
    public JSONArray read(Sheet sheet, Workbook book, int excelTemplateRowNum, int jsonRowNum) throws IOException{
        int rowStart = sheet.getFirstRowNum();    // 首行下标
        int rowEnd = sheet.getLastRowNum();    // 尾行下标
        // 如果首行+excelTemplateRowNum-1与尾行相同，表明只有excelTemplateRowNum行，直接返回空数组
        if (rowEnd == rowStart + excelTemplateRowNum - 1) {
            book.close();
            return new JSONArray();
        }
        // 获取第jsonRowNum行JSON对象键
        Row lastExcelTemplateRow = sheet.getRow(rowStart + jsonRowNum - 1);
        int cellStart = lastExcelTemplateRow.getFirstCellNum();
        int cellEnd = lastExcelTemplateRow.getLastCellNum();
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        for (int j = cellStart; j < cellEnd; j++) {
            keyMap.put(j,getValue(lastExcelTemplateRow.getCell(j), rowStart, j, book, true));
        }
        // 从第excelTemplateRowNum+1行开始获取每行JSON对象的值
        JSONArray array = new JSONArray();
        for(int i = rowStart + excelTemplateRowNum; i <= rowEnd ; i++) {
            Row eachRow = sheet.getRow(i);
            JSONObject obj = new JSONObject();
            StringBuffer sb = new StringBuffer();
            for (int k = cellStart; k < cellEnd; k++) {
                if (eachRow != null) {
                    String val = getValue(eachRow.getCell(k), i, k, book, false);
                    sb.append(val);        // 所有数据添加到里面，用于判断该行是否为空
                    obj.put(keyMap.get(k),val);
                }
            }
            if (sb.toString().length() > 0) {
                array.add(obj);
            }
        }
        book.close();
        return array;
    }

    /**
     * 获取每个单元格的数据
     * @param cell 单元格对象
     * @param rowNum 第几行
     * @param index 该行第几个
     * @param book 主要用于关闭流
     * @param isKey 是否为键：true-是，false-不是。 如果解析Json键，值为空时报错；如果不是Json键，值为空不报错
     * @return
     * @throws IOException
     */
    public String getValue(Cell cell, int rowNum, int index, Workbook book, boolean isKey) throws IOException{

        // 空白或空
        if (cell == null || cell.getCellTypeEnum() == CellType.BLANK ) {
            if (isKey) {
                book.close();
                throw new NullPointerException(String.format("the key on row %s index %s is null ", ++rowNum,++index));
            }else{
                return "";
            }
        }

        // 0. 数字 类型
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.format(date);
            }

            /*String val = cell.getNumericCellValue()+"";
            val = val.toUpperCase();
            if (val.contains("E")) {
                val = val.split("E")[0].replace(".", "");
            }*/
            //防止数字变成科学计数法的形式，使数据最后的0保留
            DecimalFormat df = new DecimalFormat("0");
            String val = df.format(cell.getNumericCellValue());

            //解析excel获得的数字去除末尾的.0
            if (val.endsWith(".0")) {
                val = val.substring(0, val.length() - 2);
            }
            return val;
        }

        // 1. String类型
        if (cell.getCellTypeEnum() == CellType.STRING) {
            String val = cell.getStringCellValue();
            if (val == null || val.trim().length()==0) {
                if (book != null) {
                    book.close();
                }
                return "";
            }
            return val.trim();
        }

        // 2. 公式 CELL_TYPE_FORMULA
        if (cell.getCellTypeEnum() == CellType.FORMULA) {
            return cell.getStringCellValue();
        }

        // 4. 布尔值 CELL_TYPE_BOOLEAN
        if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue()+"";
        }

        // 5.    错误 CELL_TYPE_ERROR
        return "";
    }
}