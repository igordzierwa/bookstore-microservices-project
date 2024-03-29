package com.agh.bookstoreProducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients
public class BookstoreProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreProductsApplication.class, args);
    }


	@Bean
	public Docket api() {
		Docket docket =  new Docket(DocumentationType.SWAGGER_2)

				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())

				.build();
		docket.pathMapping("products-service");
		return docket;
	}


	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver(){
		return new CommonsMultipartResolver();
	}
}
