package com.medilabo.microservicefrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.microservicefrontend")
public class MicroserviceFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceFrontendApplication.class, args);
	}

}
