package com.myz.starters.distribute.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzMa
 * @desc
 * 返回long 类型要注意前端js只能接受2^53的值，超过这个值就会有精度丢失(最后两位数字变成00)问题
 * 可以返回String类型
 * 历史项目返回的就是long，如何兼容？
 * @date 2020/11/5 11:13
 * @email 2641007740@qq.com
 */
@Configuration
public class SnowflakeIdWorkerConfiguration implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(SnowflakeIdWorkerConfiguration.class);

    @Value("${distribute.id.snowflake.data-center-id}")
    private int dataCenterId;

    @Value("${distribute.id.snowflake.worker-id}")
    private int workerId;

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(){
        return new SnowflakeIdWorker(workerId,dataCenterId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("dataCenterId={} workerId={}",dataCenterId,workerId);
    }
}
