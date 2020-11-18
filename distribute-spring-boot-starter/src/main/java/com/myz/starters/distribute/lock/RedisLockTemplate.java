package com.myz.starters.distribute.lock;

import com.myz.common.exception.MyzBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/6 19:19
 * @email 2641007740@qq.com
 */
public class RedisLockTemplate<R> {

    private Logger LOGGER = LoggerFactory.getLogger(RedisLockTemplate.class);

    private RedisLock redisLock;

    public RedisLockTemplate(RedisLock redisLock){
        this.redisLock = redisLock;
    }

    public R tryBiz(BizCallback<R> bizCallback,String key){
        String uidVal = generatorUid();
        long ex = 10l;
        return tryBiz(bizCallback,key,uidVal,ex);
    }

    /**
     *
     * @param bizCallback
     * @param key
     * @param ex
     * @return
     */
    public R tryBiz(BizCallback<R> bizCallback,String key,long ex){
        String uidVal = generatorUid();
        return tryBiz(bizCallback,key,uidVal,ex);
    }

    /**
     *
     * @param bizCallback
     * @param key
     * @param val
     * @param ex
     * @return
     */
    public R tryBiz(BizCallback<R> bizCallback,String key,String val,long ex){
        boolean acquired = redisLock.tryAcquire(key, val, ex);
        try {
            if (!acquired){
                throw new MyzBizException("tryAcquireFail","尝试获取锁失败",key,val,ex);
            }
            LOGGER.info("acquire lock success key={},val={},ex={}",key,val,ex);
            return bizCallback.doBiz();
        }catch (MyzBizException throwable){
            LOGGER.info("acquire lock fail key={},val={},ex={}",key,val,ex);
            throw throwable;
        }catch (Throwable throwable){
            LOGGER.error("acquire lock throwable key={},val={},ex={},cause=",key,val,ex,throwable);
            throw throwable;
        }finally {
            if (acquired){
                redisLock.release(key,val);
                LOGGER.info("release lock success key={},val={},ex={}",key,val,ex);
            }
        }
    }


    public R doBiz(BizCallback<R> bizCallback,String key) throws InterruptedException {
        String uidVal = generatorUid();
        long ex = 10l;
        long waitTime =30l;
        return this.doBiz(bizCallback,key,uidVal,ex,waitTime);
    }

    public R doBiz(BizCallback<R> bizCallback,String key,long waitTime) throws InterruptedException {
        String uidVal = generatorUid();
        long ex = 10l;
        return this.doBiz(bizCallback,key,uidVal,ex,waitTime);
    }

    public R doBiz(BizCallback<R> bizCallback,String key,long ex,long waitTime) throws InterruptedException {
        String uidVal = UUID.randomUUID().toString();
        return this.doBiz(bizCallback,key,uidVal,ex,waitTime);
    }

    /**
     *
     * @param bizCallback
     * @param key
     * @param val
     * @param ex
     * @param waitTime
     * @return
     * @throws InterruptedException
     */
    public R doBiz(BizCallback<R> bizCallback,String key,String val,long ex,long waitTime) throws InterruptedException {
        boolean acquired = false;
        try {
            acquired = redisLock.acquire(key, val, ex,waitTime);
            if (!acquired){
                throw new MyzBizException("acquireTimeout","尝试获取锁失败超时",key,val,ex,waitTime);
            }
            return bizCallback.doBiz();
        }catch (Throwable throwable){
            throw throwable;
        }finally {
            if (acquired){
                redisLock.release(key,val);
            }
        }
    }


    /**
     * 生成随机id
     * @return
     */
    private String generatorUid(){
        return UUID.randomUUID().toString();
    }
}
