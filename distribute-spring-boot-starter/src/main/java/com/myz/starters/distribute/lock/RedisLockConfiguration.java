package com.myz.starters.distribute.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.JedisCluster;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/6 16:14
 * @email 2641007740@qq.com
 */
@Configuration
public class RedisLockConfiguration {



    @Primary
    @ConditionalOnBean(JedisCluster.class)
    public JedisClusterLock jedisClusterLock(){
        return null;
    }



}
