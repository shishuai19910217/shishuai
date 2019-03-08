package com.ido85.log.utils;

import static com.ido85.log.ContextKeyConstant.BUSINESS_METHOD;
import static com.ido85.log.ContextKeyConstant.BUSINESS_METHOD_EXECUTE_ERROR;
import static com.ido85.log.ContextKeyConstant.BUSINESS_METHOD_RETURN_VALUE_KEY;
import static com.ido85.log.ContextKeyConstant.BUSINESS_OPERATION_IP;
import static com.ido85.log.ContextKeyConstant.BUSINESS_OPERATION_TIME;
import static com.ido85.log.ContextKeyConstant.BUSINESS_OPERATION_USER;
import static com.ido85.log.ContextKeyConstant.PRE_OPERATOR_OF_METHOD_KEY;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.ido85.frame.spring.InstanceFactory;
import com.ido85.log.BusinessLogExporter;
import com.ido85.log.MethodAlias;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 业务日志拦截处理器
 * @author rongxj
 */
@Named
@Aspect
@ConfigurationProperties(prefix="businesslog", locations = "application.properties")
public class BusinessLogInterceptor {

    @Value("${businesslog.enable}")
    private boolean isLogEnabled;
    
    @Inject
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Inject
    private BusinessLogExporter businessLogExporter;
    
//    @Pointcut(value = "@annotation(com.ido85.log.MethodAlias)", argNames="alias")
//    public void join(MethodAlias alias){
//    	System.out.println(alias.value());
//    }

    @AfterReturning(pointcut="@annotation(alias)", returning="result")
    public void logAfter(JoinPoint joinPoint, MethodAlias alias, Object result) {
        log(joinPoint, alias, result, null);
    }

    @AfterThrowing(pointcut="@annotation(alias)", throwing="error")
    public void afterThrowing(JoinPoint joinPoint, MethodAlias alias, Throwable error) {
        log(joinPoint, alias, null, error);
    }

    public void log(JoinPoint joinPoint, MethodAlias alias, Object result, Throwable error) {

//        String BLMappingValue = getBLMapping(joinPoint);

        /**
         * 日志开关及防止重复查询
         */
        if (!isLogEnabled() || ThreadLocalBusinessLogContext.get().get(BUSINESS_METHOD) != null) {
            return;
        }
        
        String methodName = alias.value();

        BusinessLogThread businessLogThread = new BusinessLogThread(
                Collections.unmodifiableMap(createDefaultContext(joinPoint, methodName, result, error)),
                methodName,
                getBusinessLogExporter());

        if (null == getThreadPoolTaskExecutor()) {
            businessLogThread.run();
        } else {
            getThreadPoolTaskExecutor().execute(businessLogThread);
        }
    }

    private boolean isLogEnabled() {
    	return isLogEnabled;
    }

    private Map<String, Object> createDefaultContext(JoinPoint joinPoint, String methodName,
                                                     Object result, Throwable error) {
        Map<String, Object> context = ThreadLocalBusinessLogContext.get();

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.put(PRE_OPERATOR_OF_METHOD_KEY + i, args[i]);
        }

        context.put(BUSINESS_METHOD_RETURN_VALUE_KEY, result);

        if (null != error) {
            context.put(BUSINESS_METHOD_EXECUTE_ERROR, error.getCause());
        }
        context.put(BUSINESS_OPERATION_USER, ThreadLocalBusinessLogContext.get().get(BUSINESS_OPERATION_USER));
        context.put(BUSINESS_OPERATION_IP, ThreadLocalBusinessLogContext.get().get(BUSINESS_OPERATION_IP));
        context.put(BUSINESS_METHOD, methodName);//getBLMapping(joinPoint));
        context.put(BUSINESS_OPERATION_TIME, new Date());
        return context;

    }

    /*private String getBLMapping(JoinPoint joinPoint) {
        Method method = invocationMethod(joinPoint);
        if (method.isAnnotationPresent(MethodAlias.class)) {
            return method.getAnnotation(MethodAlias.class).value();
        }
        return joinPoint.getSignature().toString();
    }*/

   /* private Method invocationMethod(JoinPoint joinPoint) {
        try {
            Field methodInvocationField = MethodInvocationProceedingJoinPoint.class.getDeclaredField("methodInvocation");
            methodInvocationField.setAccessible(true);
            ProxyMethodInvocation methodInvocation = (ProxyMethodInvocation) methodInvocationField.get(joinPoint);
            return methodInvocation.getMethod();
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }*/


    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        if (null == threadPoolTaskExecutor) {
            threadPoolTaskExecutor = InstanceFactory.getInstance(ThreadPoolTaskExecutor.class, "threadPoolTaskExecutor");
        }

        return threadPoolTaskExecutor;
    }

    public BusinessLogExporter getBusinessLogExporter() {
        if (null == businessLogExporter) {
            businessLogExporter = InstanceFactory.getInstance(BusinessLogExporter.class, "businessLogExporter");
        }
        return businessLogExporter;
    }


}
