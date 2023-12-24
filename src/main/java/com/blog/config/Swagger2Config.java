package com.blog.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Value("${swagger.enable}")
	private Boolean enable;

	@Bean
	public Docket webApiConfig(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("y-Api")
				.apiInfo(webApiInfo())
				.enable(enable) // 是否显示
				.select()
				.paths(Predicates.not(PathSelectors.regex("/error.*")))
				.build();
	}

	private ApiInfo webApiInfo(){
		return new ApiInfoBuilder()
				.title("y-Api")
				.description("y-Api")
				.version("1.0")
				.contact(new Contact("y", "http://y.com", "424559192@qq.com"))
				.build();
	}

}