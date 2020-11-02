package com.myz.starters.notice.dingding;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 17:25
 * @email 2641007740@qq.com
 */
@ConfigurationProperties(prefix = "game.notice.dingding")
public class DingDingProperties {

    private String primaryBeanName = "dingDingServicePrimary";

    private boolean enabled = true;

    private Map<String,DingDingInfo> infoMap;

    public String getPrimaryBeanName() {
        return primaryBeanName;
    }

    public void setPrimaryBeanName(String primaryBeanName) {
        this.primaryBeanName = primaryBeanName;
    }

    public Map<String, DingDingInfo> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<String, DingDingInfo> infoMap) {
        this.infoMap = infoMap;
    }

    @Override
    public String toString() {
        return "DingDingProperties{" +
                "primaryName='" + primaryBeanName + '\'' +
                ", dingInfoMap=" + infoMap +
                '}';
    }
}
