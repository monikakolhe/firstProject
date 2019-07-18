package com.example.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.security.JWTTokenInterceptor;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.*" })
@EnableMongoRepositories(basePackages = { "com.example.*" })
@CrossOrigin(origins = "*", maxAge = 3600)
public class Demo2Application extends WebMvcConfigurerAdapter {

	@Autowired
	private JWTTokenInterceptor jwtFilter;

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(Demo2Application.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtFilter)
				/* .addPathPatterns("/login/user/adminSecure/*") */
		.excludePathPatterns("/login/unsubscribeUser")
		.excludePathPatterns("/login/updateUserInfo")
		.excludePathPatterns("/login/user/info").
		excludePathPatterns("/login/user/userInfo").
		excludePathPatterns("/login/user/adminSecure/*").
		excludePathPatterns("/login/user/*").
		excludePathPatterns("/login/setPasswordFromMail").
		excludePathPatterns("/user/adminSecure/list");
		

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String allowedOrigins = environment.getProperty("cors.allowed.origins");
				registry.addMapping("/**").allowedOrigins(allowedOrigins.split(",")).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
			}

		};
	}

}
