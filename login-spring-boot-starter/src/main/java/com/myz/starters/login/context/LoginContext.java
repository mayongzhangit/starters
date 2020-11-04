package com.myz.starters.login.context;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/19 12:29 AM
 * @email 2641007740@qq.com
 */
public class LoginContext {

    private LoginContext() {
    }

    private static ThreadLocal<String> userTL = new ThreadLocal<>();

    /**
     * 设置
     *
     * @param userJson
     */
    public static void set(String userJson) {
        userTL.set(userJson);
    }

    /**
     * 获取
     *
     * @return
     */
    public static String get() {
        return userTL.get();
    }

    /**
     * 移除
     */
    public static void remove() {
        userTL.remove();
    }

}
