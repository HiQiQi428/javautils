package org.luncert.springauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

}