package com.naver.test.notice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 성능 모니터링을 위한 AOP
 */
@Aspect
@Component
public class PerformanceMonitoringAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitoringAspect.class);
    
    /**
     * 서비스 레이어 메소드 실행 시간 측정
     */
    @Around("execution(* com.naver.test.notice.service.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            if (executionTime > 1000) {
                logger.warn("성능 경고 - {}.{} 실행시간: {}ms", className, methodName, executionTime);
            } else {
                logger.debug("성능 측정 - {}.{} 실행시간: {}ms", className, methodName, executionTime);
            }
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("메소드 실행 중 예외 발생 - {}.{} 실행시간: {}ms, 예외: {}", 
                        className, methodName, executionTime, e.getMessage());
            throw e;
        }
    }
    
    /**
     * 컨트롤러 메소드 실행 시간 측정
     */
    @Around("execution(* com.naver.test.notice.controller.*.*(..))")
    public Object measureControllerExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            logger.info("컨트롤러 실행시간 - {} : {}ms", methodName, executionTime);
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("컨트롤러 실행 중 예외 발생 - {} 실행시간: {}ms, 예외: {}", 
                        methodName, executionTime, e.getMessage());
            throw e;
        }
    }
}
