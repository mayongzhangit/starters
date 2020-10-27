package com.myz.starters.dynamic.refresh.sample.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/17 15:18
 * @email 2641007740@qq.com
 */

public class MyConfigChangeEvent extends ApplicationEvent {

    private String configKey;
    private String changedVal;
    public MyConfigChangeEvent(Object source, String configKey, String changedVal) {
        super(source);
        this.configKey = configKey;
        this.changedVal = changedVal;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getChangedVal() {
        return changedVal;
    }

    public void setChangedVal(String changedVal) {
        this.changedVal = changedVal;
    }

    @Override
    public String toString() {
        return "ConfigChangeEvent{" +
                "configKey='" + configKey + '\'' +
                ", changedVal='" + changedVal + '\'' +
                '}';
    }
}
