package com.myz.starters.distribute.id;

import com.myz.starters.distribute.id.SnowflakeIdWorker;
import com.myz.starters.distribute.id.SnowflakeInfo;
import com.myz.starters.distribute.sample.DistributeStartApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/5 17:10
 * @email 2641007740@qq.com
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DistributeStartApp.class)
public class SnowflakeIdWorkerTest {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 我擦，就这数据来看，并发小的时候以为SnowFlake的最后几位是从0 开始 每次并发+1，没有并发也就是0，导致产生的id是偶数
     * 当使用分库分表用了0数个db的时候就很蛋疼，特别是用了2个db的时候，没有什么并发的情况下，数据全部达到了db0上，如果db0的表的数量也是2个的话，全达到了table0上  。。。。
     * @throws InterruptedException
     */
    @Test
    public void nextId() throws InterruptedException {

        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());

        Thread.sleep(10);
        log.info("after sleep id={}",snowflakeIdWorker.nextId());

        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());


        Thread.sleep(10);
        log.info("after sleep id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());

        Thread.sleep(10);
        log.info("after sleep id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());


        Thread.sleep(10);
        log.info("after sleep id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());
        log.info("id={}",snowflakeIdWorker.nextId());

        long id = snowflakeIdWorker.nextId();
        SnowflakeInfo snowflakeInfo = snowflakeIdWorker.extraId(id);
        // 这咋Assert

        log.info("id={}",id);
        log.info("snowflakeInfo={}",snowflakeInfo);
    }
}
