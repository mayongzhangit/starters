package com.myz.starters.notice;

import com.myz.starters.notice.dingding.DingDingNoticeConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 17:25
 * @email 2641007740@qq.com
 */
@Configuration
@Import(DingDingNoticeConfiguration.class)
public class NoticeAutoConfiguration {

}
