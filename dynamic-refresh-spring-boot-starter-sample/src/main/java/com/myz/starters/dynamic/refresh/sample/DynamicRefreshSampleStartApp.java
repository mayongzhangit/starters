package com.myz.starters.dynamic.refresh.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author yzMa
 * @desc @see {@link WebMvcAutoConfiguration.EnableWebMvcConfiguration}
 * @date 2020/7/17 12:52
 * @email 2641007740@qq.com
 */
@ServletComponentScan("com.myz.starters.dynamic.refresh.sample.servlet")
@SpringBootApplication
public class DynamicRefreshSampleStartApp {

    public static void main(String[] args) {
        SpringApplication.run(DynamicRefreshSampleStartApp.class,args);
    }

//    static class WebConfig implements WebMvcConfigurer{
//        @Override
//        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//            System.out.println(converters);
//            for (HttpMessageConverter httpMessageConverter : converters){
//                boolean equals = httpMessageConverter.getClass().getSimpleName().equals(StringHttpMessageConverter.class.getSimpleName());
//                if (equals){ //解决乱码问题
//                    StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter)httpMessageConverter;
//                    stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
//                }
//            }
//        }
//    }

}
