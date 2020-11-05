package com.myz.starters.distribute;

import com.myz.starters.distribute.id.SnowflakeIdWorkerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/23 15:59
 * @email 2641007740@qq.com
 */
@Configuration
@Import(SnowflakeIdWorkerConfiguration.class)
public class DistributeIdAutoConfiguration {
}
