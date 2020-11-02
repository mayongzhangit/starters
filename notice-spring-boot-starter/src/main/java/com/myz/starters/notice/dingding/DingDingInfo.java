package com.myz.starters.notice.dingding;

import java.io.Serializable;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 17:36
 * @email 2641007740@qq.com
 */
public class DingDingInfo implements Serializable {

    private String accessToken;

    private String secret;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "DingDingInfo{" +
                "accessToken='" + accessToken + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
