package com.myz.starters.notice.sample;

import com.myz.starters.notice.dingding.DingDingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 18:12
 * @email 2641007740@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoticeStartApp.class)
public class DingDingServiceTest {
    @Autowired
//    @Resource(name = "dingdingService")
    private DingDingService dingDingService;

    @Test
    public void sendTextMsg(){
        dingDingService.sendTextMsg("用作测试，忽略即可。");
    }
}
