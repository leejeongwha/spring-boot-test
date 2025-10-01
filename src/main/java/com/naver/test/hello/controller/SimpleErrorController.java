package com.naver.test.hello.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/error")
public class SimpleErrorController implements ErrorController {
	private final ErrorAttributes errorAttributes;

	@Autowired
	public SimpleErrorController(ErrorAttributes errorAttributes) {
		Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping
	public Map<String, Object> error(HttpServletRequest aRequest) {
		Map<String, Object> body = getErrorAttributes(aRequest, getTraceParameter(aRequest));
		return body;
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equalsIgnoreCase(parameter);
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
		WebRequest webRequest = new ServletWebRequest(aRequest);
		ErrorAttributeOptions options = includeStackTrace ? 
			ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE) : 
			ErrorAttributeOptions.defaults();
		return errorAttributes.getErrorAttributes(webRequest, options);
	}
}
