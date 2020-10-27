package com.myz.starters.dynamic.refresh.sample.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/17 15:12
 * @email 2641007740@qq.com
 */
public class PrintUtil {

    public static void print(Object object){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        System.out.println(format+" "+Thread.currentThread().getName()+" "+object);
    }
}
