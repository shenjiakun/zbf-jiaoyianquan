package com.sjk.es.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 作者: LCG
 * 日期: 2020/7/28 09:06
 * 描述:
 */
@Component
public class MyTask01 implements SimpleJob {

    @Value("${server.port}")
    private String port;

    @Override
    public void execute(ShardingContext shardingContext) {
       // System.out.println("==========将来大家写自己的业务==========");

        //获取分片的总个数
        int shardingTotalCount = shardingContext.getShardingTotalCount();

        //当前运行的分片的下标
        int shardingItem = shardingContext.getShardingItem();


         if(0==shardingItem){
             System.out.println(port+"==============0-3000"+"--->"+    shardingContext.getShardingParameter());
         }else if(1==shardingItem){
             System.out.println(port+"==============3001-6000");
         }else if(2==shardingItem){
             System.out.println(port+"==============6001-10000");
         }



    }
}
