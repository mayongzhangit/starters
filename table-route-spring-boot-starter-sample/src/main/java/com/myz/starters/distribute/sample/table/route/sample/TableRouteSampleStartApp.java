package com.myz.starters.distribute.sample.table.route.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 15:16
 * @email 2641007740@qq.com
 */
@MapperScan()
@SpringBootApplication
public class TableRouteSampleStartApp {

    public static void main(String[] args) {

        SpringApplication.run(TableRouteSampleStartApp.class,args);
    }
}
