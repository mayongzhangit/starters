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

    @Test
    public void nextId(){
        long id = snowflakeIdWorker.nextId();
        id = snowflakeIdWorker.nextId();
        id = snowflakeIdWorker.nextId();
        id = snowflakeIdWorker.nextId();
        id = snowflakeIdWorker.nextId();
        id = snowflakeIdWorker.nextId();
        id = snowflakeIdWorker.nextId();
        id = snowflakeIdWorker.nextId();

        SnowflakeInfo snowflakeInfo = snowflakeIdWorker.extraId(id);
        // 这咋Assert
        log.info("id={}",id);
        log.info("snowflakeInfo={}",snowflakeInfo);
    }
}
