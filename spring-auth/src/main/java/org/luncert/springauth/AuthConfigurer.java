package org.luncert.springauth;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfigurer implements WebMvcConfigurer {

    /**
     * 动态注册Bean: AuthManager
     */
	@Bean
	public AuthManager authManager() { return new AuthManager(); }
    
	@Bean
    public AuthInterceptor authInterceptor() { return new AuthInterceptor(); }
    
    /**
     * 配置拦截器: AuthInterceptor
     */
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(authInterceptor());
		registration.addPathPatterns("/**");
        registration.order(1);
    }
    
    /**
     * 配置方法参数解析器: UserMethodArgumentResolver
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserMethodArgumentResolver());
    }

}