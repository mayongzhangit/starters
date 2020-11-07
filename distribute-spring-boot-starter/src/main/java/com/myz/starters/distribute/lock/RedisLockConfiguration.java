package com.myz.starters.distribute.lock;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    public JedisClusterLock jedisClusterLock(JedisCluster jedisCluster){
        return new JedisClusterLock(jedisCluster);
    }

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    public RedisTemplateLock redisTemplateLock(StringRedisTemplate stringRedisTemplate){
        return new RedisTemplateLock(stringRedisTemplate);
    }

    @Bean
    public RedisLockTemplate redisLockTemplate(RedisLock redisLock){
        return new RedisLockTemplate(redisLock);
    }
}
