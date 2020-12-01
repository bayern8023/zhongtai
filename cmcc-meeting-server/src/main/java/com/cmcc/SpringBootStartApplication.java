package com.cmcc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class SpringBootStartApplication extends SpringBootServletInitializer {
 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MeetingApplication.class);  //这里要指向原先用main方法执行的FHmainApplication启动类
    }
}