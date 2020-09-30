package com.sjk.es.esconfig;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import com.sjk.es.task.MyTask01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 作者: LCG
 * 日期: 2020/7/28 09:03
 * 描述:
 */
@Configuration
public class EsConfig {

    @Autowired
    private EsZkConfig esZkConfig;


    @Autowired
    private MyTask01 myTask01;
 /*   @Autowired
    private MyTask02 myTask02;*/

    /**
     * Zookeeper的配置
     * @param esZkConfig
     * @return
     */
    @Bean("zookeeperConfiguration")
    public ZookeeperConfiguration zookeeperConfiguration(EsZkConfig esZkConfig){
        ZookeeperConfiguration zookeeperConfiguration=new ZookeeperConfiguration(esZkConfig.getList(),esZkConfig.getNamespace());
        return zookeeperConfiguration;
    }


    /**
     * 配置Zookeeper注册中心
     * @param zookeeperConfiguration
     * @return
     */
    @Bean(initMethod = "init",value = "zookeeperRegistryCenter")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration zookeeperConfiguration){
        ZookeeperRegistryCenter zookeeperRegistryCenter=new ZookeeperRegistryCenter(zookeeperConfiguration);
        return zookeeperRegistryCenter;
    }

    /**
     * job的核心配置
     * @return
     */
   @Bean("jobCoreConfiguration")
   public JobCoreConfiguration jobCoreConfiguration(){
       JobCoreConfiguration simpleCoreConfig =
               JobCoreConfiguration.newBuilder("mytask01", "0/5 * * * * ?", 3)
                       .description("描述任务的信息")
                       .failover(true)
                       .jobParameter("可以为任务传入一些固定的参数")
                       .shardingItemParameters("0=T00,1=T01,2=T02")
                       .build();

       return simpleCoreConfig;

   }

/*

    */
/**
     * job的核心配置2
     * @return
     *//*

    @Bean("jobCoreConfiguration2")
    public JobCoreConfiguration jobCoreConfiguration2(){
        JobCoreConfiguration simpleCoreConfig =
                JobCoreConfiguration.newBuilder("mytask02", "0/10 * * * * ?", 3)
                        .description("描述任务的信息02")
                        .failover(true)
                        .jobParameter("可以为任务传入一些固定的参数02")
                        .shardingItemParameters("0=T200,1=T201,2=T202")
                        .build();

        return simpleCoreConfig;

    }
*/



    /**
     * 配置任务
     * @param jobCoreConfiguration
     * @return
     */
   @Bean("simpleJobConfiguration")
   public SimpleJobConfiguration simpleJobConfiguration(JobCoreConfiguration jobCoreConfiguration){
       SimpleJobConfiguration simpleJobConfiguration=new SimpleJobConfiguration(jobCoreConfiguration,MyTask01.class.getCanonicalName());
       return simpleJobConfiguration;
   }



   /* *//**
     * 配置任务
     * @param
     * @return
     *//*
    @Bean("simpleJobConfiguration2")
    public SimpleJobConfiguration simpleJobConfiguration2(JobCoreConfiguration jobCoreConfiguration2){
        SimpleJobConfiguration simpleJobConfiguration=new SimpleJobConfiguration(jobCoreConfiguration2,MyTask02.class.getCanonicalName());
        return simpleJobConfiguration;
    }

*/

   @Bean("liteJobConfiguration")
   public LiteJobConfiguration liteJobConfiguration(SimpleJobConfiguration simpleJobConfiguration){
         // 定义Lite作业根配置
       LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration).build();
       return simpleJobRootConfig;
   }


   /* @Bean("liteJobConfiguration2")
    public LiteJobConfiguration liteJobConfiguration2(SimpleJobConfiguration simpleJobConfiguration2){
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfiguration2).build();
        return simpleJobRootConfig;
    }*/


    /**
     * 配置并初始化调度任务
     * @param zookeeperRegistryCenter
     * @param liteJobConfiguration
     * @return
     */
   @Bean(value = "jobScheduler",initMethod = "init")
   public JobScheduler jobScheduler(ZookeeperRegistryCenter zookeeperRegistryCenter,LiteJobConfiguration liteJobConfiguration){
      // JobScheduler jobScheduler=new JobScheduler(zookeeperRegistryCenter,liteJobConfiguration);

       SpringJobScheduler springJobScheduler=new SpringJobScheduler(myTask01,zookeeperRegistryCenter,liteJobConfiguration);

       return springJobScheduler;
   }



   /* *//**
     * 配置并初始化调度任务
     * @param zookeeperRegistryCenter
     * @param liteJobConfiguration2
     * @return
     *//*
    @Bean(value = "jobScheduler2",initMethod = "init")
    public JobScheduler jobScheduler2(ZookeeperRegistryCenter zookeeperRegistryCenter,LiteJobConfiguration liteJobConfiguration2){
        // JobScheduler jobScheduler=new JobScheduler(zookeeperRegistryCenter,liteJobConfiguration);

        SpringJobScheduler springJobScheduler=new SpringJobScheduler(myTask02,zookeeperRegistryCenter,liteJobConfiguration2);

        return springJobScheduler;
    }*/

}
