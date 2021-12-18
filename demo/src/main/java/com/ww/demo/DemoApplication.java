package com.ww.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableCaching
public class DemoApplication {

	public static void main(String[] args) {
		log.debug("DemoApplication");		
		SpringApplication.run(DemoApplication.class, args);
	}

}
