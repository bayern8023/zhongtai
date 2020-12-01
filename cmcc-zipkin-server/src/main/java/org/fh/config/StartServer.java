package org.fh.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1) // 1 代表启动顺序
public class StartServer implements ApplicationRunner{
	
	@Override
    public void run(ApplicationArguments var1) throws Exception{
		System.out.println("-------------------------(Zipkin服务跟踪)系统启动成功-------------------------");
    }

}