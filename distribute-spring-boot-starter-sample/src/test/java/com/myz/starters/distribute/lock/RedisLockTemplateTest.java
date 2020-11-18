package com.myz.starters.distribute.lock;

import com.myz.starters.distribute.sample.DistributeStartApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/18 10:25
 * @email 2641007740@qq.com
 */
@Slf4j
@ActiveProfiles("redis")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DistributeStartApp.class)
public class RedisLockTemplateTest {

    @Autowired
    private RedisLockTemplate redisLockTemplate;

    @Test
    public void lock() throws InterruptedException {
        int size = 10;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        ExecutorService executorService = Executors.newFixedThreadPool(size);

        for (int i=0;i<size;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        redisLockTemplate.tryBiz(new BizCallback() {
                            @Override
                            public Object doBiz() {
                                log.info("get lock success start do work");
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                log.info("job done start release");

                                countDownLatch.countDown();

                                return "this is you data";
                            }
                        },"bbsss");
                    }catch (Exception e){
                        countDownLatch.countDown();
                    }

                }
            });
        }

        countDownLatch.await();
        log.info("test done");
    }
}
