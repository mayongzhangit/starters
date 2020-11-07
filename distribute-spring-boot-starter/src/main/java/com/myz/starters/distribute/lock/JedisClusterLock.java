package com.myz.starters.distribute.lock;

import redis.clients.jedis.JedisCluster;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/6 16:27
 * @email 2641007740@qq.com
 */
public class JedisClusterLock {

    private JedisCluster jedisCluster;

    public JedisClusterLock(JedisCluster jedisCluster){
        this.jedisCluster = jedisCluster;
    }
}
