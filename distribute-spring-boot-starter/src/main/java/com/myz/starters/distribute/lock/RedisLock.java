package com.myz.starters.distribute.lock;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/6 16:58
 * @email 2641007740@qq.com
 */
public interface RedisLock {

    /**
     * 尝试获取锁
     * @param key
     * @param val
     * @param ex
     * @return
     */
    boolean tryAcquire(String key,String val,long ex);

    /**
     * 在指定时间内，多次尝试获取锁，直到超时或者获取锁成功
     * @param key
     * @param val
     * @param ex
     * @param waitTime
     * @return
     * @throws InterruptedException
     */
    boolean acquire(String key,String val,long ex,long waitTime) throws InterruptedException;

    /**
     * 释放锁，val为 tryAcquire 或acquire的val是同一个
     * @param key
     * @param val
     * @return
     */
    boolean release(String key,String val);


}
