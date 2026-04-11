package core.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import core.interceptor.AdminInterceptor;
import core.interceptor.EmployeeInterceptor;
import web.chart.websocket.ChartWebSocket;

@Configuration
@EnableWebMvc
@EnableWebSocket
@ComponentScan(basePackages = { "web", "core.exception" }, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class) })
public class SpringMvcConfig implements WebMvcConfigurer,WebSocketConfigurer  {
	
	@Autowired
	private EmployeeInterceptor employeeInterceptor;

	@Autowired
	private AdminInterceptor adminInterceptor;
	
    @Autowired
    private ChartWebSocket chartWebSocket;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chartWebSocket, "/ws/admin/chart")
                .setAllowedOrigins("*");
    }
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		messageConverter.setPrettyPrint(true);
		converters.add(messageConverter);
		converters.add(new ByteArrayHttpMessageConverter());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOriginPatterns("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true)
				.maxAge(3600);
	}

	// 註冊檢查登入攔截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 後台
		// TODO: [MOCK] 正式上線前移除 "/admin/mock-login" 的 excludePathPatterns
		registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**")
				.excludePathPatterns("/admin/login", "/admin/mock-login","/admin/checkLogin"); // 放行後台登入api

		// 前台
		registry.addInterceptor(employeeInterceptor).addPathPatterns("/frontUser/**")
				.excludePathPatterns("/frontUser/employee/login", "/frontUser/mock-login/**","/frontUser/employee/checkLogin"); // 放行前台登入api
	}
}
