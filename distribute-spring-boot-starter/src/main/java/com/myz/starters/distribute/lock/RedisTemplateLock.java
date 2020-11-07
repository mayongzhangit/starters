package com.myz.starters.distribute.lock;

import com.myz.common.exception.MyzBizException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/6 16:27
 * @email 2641007740@qq.com
 */
public class RedisTemplateLock implements RedisLock{

    private StringRedisTemplate stringRedisTemplate ;

    public RedisTemplateLock(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     *
     * @param key
     * @param val
     * @param ex
     * @return
     */
    @Override
    public boolean tryAcquire(String key,String val,long ex){
        return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.set(key.getBytes(), val.getBytes(), Expiration.seconds(ex), RedisStringCommands.SetOption.SET_IF_ABSENT);
            }
        });

    }

    /**
     *
     * @param key
     * @param val
     * @param ex
     * @param waitTime
     * @return
     */
    @Override
    public boolean acquire(String key,String val,long ex,long waitTime) throws InterruptedException {
        long currentTimestamp = System.currentTimeMillis();
        long deadLineTimestamp = currentTimestamp + waitTime;

        Boolean setOk = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.set(key.getBytes(), val.getBytes(), Expiration.seconds(ex), RedisStringCommands.SetOption.SET_IF_ABSENT);
            }
        });
        if (setOk){
            return true;
        }

        while (deadLineTimestamp < (currentTimestamp = System.currentTimeMillis())){
            Thread.sleep(100);

            setOk = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.set(key.getBytes(), val.getBytes(), Expiration.seconds(ex), RedisStringCommands.SetOption.SET_IF_ABSENT);
                }
            });
        }
        if (!setOk){
            return false;
        }
        return true;
    }



    /**
     *
     * @param key
     * @param val
     * @return
     */
    @Override
    public boolean release(String key,String val){
        // EVAL script numkeys key [key ...] arg [arg ...]
        // numkeys 是key的个数，后边接着写key1 key2...  val1 val2....
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = key.getBytes();
                byte[] valBytes = val.getBytes();

                Long eval = (Long)connection.eval(script.getBytes(), ReturnType.INTEGER, 1, keyBytes, valBytes);
                if (1l == eval){ //可以用== 数大在用equals
                    return true;
                }
                return false;
            }
        });

    }



}
