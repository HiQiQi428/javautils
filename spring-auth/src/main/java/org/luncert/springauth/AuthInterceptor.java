package org.luncert.springauth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.luncert.simpleutils.ContentType;
import org.luncert.simpleutils.IOHelper;
import org.luncert.springauth.UserProxy;
import org.luncert.springauth.annotation.AuthRequired;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static Map<String, UserProxy> map = new HashMap<>();

    private static Map<Long, String> waitingMap = new HashMap<>();

    private static String hashcode(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = raw.getBytes();
            md.update(input);
            byte[] md5Bytes = md.digest();
            BigInteger bigInteger = new BigInteger(1, md5Bytes);
            return bigInteger.toString(16);
		} catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
		}
    }

    protected static void grant(int accessLevel, Object user) {
        grant(new UserProxy(accessLevel, user));
    }

    protected static void grant(int accessLevel, Object user, int ttl) {
        grant(new UserProxy(accessLevel, user, ttl));
    }

    private synchronized static void grant(UserProxy userProxy) {
        String authId = hashcode(new Date().toString());
        map.put(authId, userProxy);
        // 一个线程一段时间内只有一个http响应，所以可以根据线程id进行授权
        waitingMap.put(Thread.currentThread().getId(), authId);
    }

    protected synchronized static Object getUser(String authId) {
        UserProxy userProxy = map.get(authId);
        if (userProxy != null) return userProxy.getUser();
        else return null;
    }
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod methodHandler = (HandlerMethod)handler;
        AuthRequired authRequired = methodHandler.getMethodAnnotation(AuthRequired.class);
        HttpSession session = request.getSession();
        if (authRequired != null) {
            String authId = (String)session.getAttribute("spring-auth-id");
            if (authId != null) {
                UserProxy userProxy = map.get(authId);
                if (userProxy != null) {
                    // 判断授权是否过期
                    if (userProxy.isExpired()) {
                        map.remove(authId);
                        session.removeAttribute("spring-auth-id");
                    }
                    else {
                        userProxy.updateLastAccessTime();
                        // 验证用户访问级别
                        if (userProxy.getAccessLevel() >= authRequired.accessLevel()) {
                            return true;
                        }
                    }
                }
            }
            // authId不存在/授权已过期/不合法的用户身份
            IOHelper.writeResponse(ContentType.CONTENT_TYPE_HTML, "access rejected".getBytes(), response, true);
            return false;
        }
        else return true;
    }

	@Override
	public synchronized void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        long threadId = Thread.currentThread().getId();
        String authId = waitingMap.get(threadId);
        if (authId != null) {
            waitingMap.remove(threadId);
            request.getSession().setAttribute("spring-auth-id", authId);
        }
	}
    
}