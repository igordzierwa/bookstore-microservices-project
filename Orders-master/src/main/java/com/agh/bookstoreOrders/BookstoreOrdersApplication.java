package com.agh.bookstoreOrders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients
public class BookstoreOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreOrdersApplication.class, args);
    }

	@Bean
	public Docket api() {
		Docket docket =  new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.agh.bookstoreOrders"))
				.paths(PathSelectors.any())
				.build();

		docket.pathMapping("orders-service");
		return docket;
	}

}
