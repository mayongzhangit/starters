package com.myz.starters.distribute.sample.table.route.autoconfigure.route;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/29 9:53
 * @email 2641007740@qq.com
 */
public interface TableRouter {

    /**
     *
     * @param routeField 表路由key
     * @return 路由表的后缀值
     */
    String route(String routeField);
}
