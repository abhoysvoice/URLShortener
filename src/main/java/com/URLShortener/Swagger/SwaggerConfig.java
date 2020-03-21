package com.URLShortener.Swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
   @Bean
   public Docket apiDocket() {
       
       Docket docket =  new Docket(DocumentationType.SWAGGER_2)
    		   .useDefaultResponseMessages(false)
    		   .select()
               .apis(RequestHandlerSelectors.basePackage("com.URLShortener"))
               .paths(PathSelectors.any())
               .build()
       			.apiInfo(getDescriptionData());
       
       return docket;
       
    } 
   @SuppressWarnings("rawtypes")
private ApiInfo getDescriptionData() {
	Contact contact = new Contact("David McWeeney", "", "mcweeneydavid@gmail.com");
	List<VendorExtension> vendors = new ArrayList<VendorExtension>();
	ApiInfo apiInfo = new ApiInfo(
			   "David's URL Shortener API Application",
			   "Two URL Shortener APIs Provided:\n"
			   +"- convert a long url to a short url\n"
			   +"- revert a short URL to original\n\n"
			   +"And Two Statistics APIs on the above Functionality\n"
			   +"- Count total URLs Shortened\n"
			   +"- Count URLs Shortened on a given day",
			   "1.0",
			   "https://media0.giphy.com/media/1xoqQy70KZ98v9JOLM/giphy.gif",
			   contact ,
			   "",
			   "",
			   vendors
			);
	   return apiInfo;
   }
}


