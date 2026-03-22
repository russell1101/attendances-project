package core.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import core.interceptor.AdminInterceptor;
import core.interceptor.EmployeeInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "web", "core.exception" }, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class) })
public class SpringMvcConfig implements WebMvcConfigurer {
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		messageConverter.setPrettyPrint(true);
		converters.add(messageConverter);
		converters.add(new ByteArrayHttpMessageConverter());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowCredentials(true).maxAge(3600);
	}

	// 註冊檢查登入攔截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 後台
		registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**")
				.excludePathPatterns("/admin/employee/login"); // 放行後台登入api

		// 前台
		registry.addInterceptor(new EmployeeInterceptor()).addPathPatterns("/frontUser/**")
				.excludePathPatterns("/frontUser/employee/login", "/frontUser/mock-login/**"); // 放行前台登入api
	}
}
