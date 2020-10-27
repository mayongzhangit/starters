package com.myz.starters.aspect.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/7 20:13
 * @email 2641007740@qq.com
 */
@Slf4j
@SpringBootApplication
public class AspectSampleStartApp implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(AspectSampleStartApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("application.yml配置的logging.level.yourloggername 优先级 > logger标签设置的level");
        log.info("1）logback-spring.xml中配置了log name=PARAM-RETVAL-PRINTER 并且 application.yml配置了logging.level.PARAM-RETVAL-PRINTER: debug 则输出到PARAM-RETVAL-PRINTER的appender中");
        log.info("2）不满足1）就按照logName打印，只能是info级别，如果使用@Slf4j(topic=) 那么需要在添加一个@LoggerName(topic)才能生效，因为@Slfj是source阶段生效");
    }
}