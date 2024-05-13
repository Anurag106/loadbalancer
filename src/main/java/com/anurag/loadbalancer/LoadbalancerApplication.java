package com.anurag.loadbalancer;

import com.anurag.loadbalancer.handler.demo.server.ServerAction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class LoadbalancerApplication {
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}

	public static void main(String[] args) {
		ServerAction.starter();
		ServerAction.starter();
		SpringApplication.run(LoadbalancerApplication.class, args);
	}

}
