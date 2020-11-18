package com.myz.starters.distribute;

import com.myz.starters.distribute.lock.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisCluster;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/23 15:58
 * @email 2641007740@qq.com
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
//@Import(RedisLockConfiguration.class)// 这样会直接import到beanFactory中，比xxAutoConfiguration这样的永远早
public class DistributeLockAutoConfiguration {


    @Primary
    @ConditionalOnBean(JedisCluster.class)
    public JedisClusterLock jedisClusterLock(JedisCluster jedisCluster){
        return new JedisClusterLock(jedisCluster);
    }

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    public RedisTemplateLock redisTemplateLock(StringRedisTemplate stringRedisTemplate){
        return new RedisTemplateLock(stringRedisTemplate);
    }

    @Bean
    @ConditionalOnProperty(prefix = "distribute.lock.redis",name = "enabled")
    public RedisLockTemplate redisLockTemplate(RedisLock redisLock){
        return new RedisLockTemplate(redisLock);
    }
}
