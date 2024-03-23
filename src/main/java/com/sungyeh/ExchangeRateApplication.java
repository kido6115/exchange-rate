package com.sungyeh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sungyeh.service.ExchangeRateService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@SpringBootApplication
public class ExchangeRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateApplication.class, args);
	}

	@Resource
	private ExchangeRateService service;
	@PostConstruct
	public void init(){
			service.sync();
	}

}
