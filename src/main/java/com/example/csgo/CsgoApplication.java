package com.example.csgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
//@EnableCaching
//public class CsgoApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(CsgoApplication.class, args);
//	}
//
//	@Bean
//	public TomcatServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//		tomcat.setPort(3090);
//		return tomcat;
//	}
//}


@SpringBootApplication
@EnableCaching
public class CsgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsgoApplication.class, args);
	}

}
