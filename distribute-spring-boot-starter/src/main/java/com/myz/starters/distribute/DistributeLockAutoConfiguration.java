package com.myz.starters.distribute;

import com.myz.starters.distribute.lock.RedisLockConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/23 15:58
 * @email 2641007740@qq.com
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Import(RedisLockConfiguration.class)
public class DistributeLockAutoConfiguration {
}
