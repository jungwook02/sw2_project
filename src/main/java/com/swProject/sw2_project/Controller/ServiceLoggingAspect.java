package com.swProject.sw2_project.Controller;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    // Controller와 Service의 모든 메서드에 대해 로그를 찍기 위한 포인트컷
    @Pointcut("execution(* com.swProject.sw2_project.controller..*(..)) || execution(* com.swProject.sw2_project.service..*(..))")
    public void serviceLayer() {}

    // serviceLayer() 포인트컷이 실행되기 전에 로그 찍기
    @Before("serviceLayer()")
    public void logBefore() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // 0번 인덱스부터 출력해서 로그에 어떤 호출이 발생했는지 확인
        logger.debug("Stack trace:");
        for (StackTraceElement element : stackTrace) {
            logger.debug(element.toString());
        }

        // 서비스/컨트롤러 호출 로그 찍기
        StackTraceElement element = stackTrace[2];  // 메서드 호출 위치를 2번째 인덱스로 설정
        String className = element.getClassName();
        String methodName = element.getMethodName();
        logger.info("Calling {}.{}", className, methodName);  // 로그 출력
    }
}
