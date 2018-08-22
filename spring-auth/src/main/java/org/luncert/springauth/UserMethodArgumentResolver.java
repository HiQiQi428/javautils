package org.luncert.springauth;

import javax.servlet.http.HttpServletRequest;

import org.luncert.springauth.annotation.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String authId = (String) request.getSession().getAttribute("spring-auth-id");
		Object user = AuthInterceptor.getUser(authId);
		Class<?> klass = parameter.getParameterType();
		if (klass.isAssignableFrom(user.getClass()))
			return user;
		else
			return null;
	}

}