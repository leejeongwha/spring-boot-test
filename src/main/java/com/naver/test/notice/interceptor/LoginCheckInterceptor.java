package com.naver.test.notice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.naver.test.notice.annotation.AuthCheck;

public class LoginCheckInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		try {
			logger.info("session check : " + arg0.getSession().getAttribute("admin"));
			
			// 정적 리소스나 다른 핸들러의 경우 HandlerMethod가 아닐 수 있으므로 체크
			if (!(arg2 instanceof HandlerMethod)) {
				return true; // 정적 리소스 등은 인증 체크를 하지 않음
			}
			
			HandlerMethod handlerMethod = (HandlerMethod) arg2;
			AuthCheck authCheck = handlerMethod.getMethodAnnotation(AuthCheck.class);

			if (authCheck == null) {
				return true;
			} else {
				// login이라는 세션key를 가진 정보가 널일경우 로그인페이지로 이동
				if (arg0.getSession().getAttribute("admin") == null) {
					arg1.sendRedirect("/login/form");
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("Error in LoginCheckInterceptor: ", e);
		}

		return true;
	}

}
