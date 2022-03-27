package com.tmonet.common.aop;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.tmonet.common.util.Converter;
import com.tmonet.kms.mgmt.common.model.BaseRequest;

@Aspect
@Component
@Order(value = 2)
public class BaseLogging {

    private static final Logger logger = LoggerFactory.getLogger(BaseLogging.class);

    @Around("execution(* com.tmonet..controller.*Controller.*(..))")
    public Object controllerBaseLogging(ProceedingJoinPoint pjp) throws Throwable {
        long requestTime = System.currentTimeMillis();
        long responseTime = System.currentTimeMillis();
        Object ret;
        String target = pjp.getTarget().toString();
        String methodName = pjp.getSignature().getName();
        String classPath = target.substring(0, target.lastIndexOf("@"));
        String className = classPath.substring(classPath.lastIndexOf(".") + 1, classPath.length());
        
        try {
            logger.info("################################################################################");
            logger.info("# START CONTROLLER: {}.{}()", className, methodName);
            
            List<Object> controllerParam = Arrays.asList(pjp.getArgs());
            for (Object o : controllerParam) {
                if (o instanceof BaseRequest) {
                    logger.info("# REQUEST MESSAGE : {}", Converter.toPrettyString(o));
                } else if(o != null){
            		logger.info("# PARAM ({}) : {}", o.getClass().getName(), o.toString());
                }
            }
            
            requestTime = System.currentTimeMillis();
            ret = pjp.proceed();
            responseTime = System.currentTimeMillis();
            logger.info("# RESPONSE MESSAGE: {}", Converter.toPrettyString(ret));
            logger.info("# RESPONSE TIME   : {}ms", responseTime - requestTime);
            logger.info("# END CONTROLLER  : {}.{}()", className, methodName);
            logger.info("################################################################################");
        } catch (Throwable ex) {
            responseTime = System.currentTimeMillis();
            logger.error("! Exception Error : ", ex);
            logger.info("# RESPONSE TIME   : {}ms", responseTime - requestTime);
            logger.info("# END CONTROLLER  : {}.{}()", className, methodName);
            logger.info("################################################################################");

            throw ex;
        }

        return ret;
    }

    @Around("execution(* com.tmonet..service.*Service.*(..))")
    public Object serviceBaseLogging(ProceedingJoinPoint pjp) throws Throwable {
        Object ret;
        String target = pjp.getTarget().toString();
        String methodName = pjp.getSignature().getName();
        String classPath = target.substring(0, target.lastIndexOf("@"));
        String className = classPath.substring(classPath.lastIndexOf(".") + 1, classPath.length());
        
        try {
            logger.info("--------------------------------------------------------------------------------");
            logger.info("- Start Service: {}.{}", className, methodName);
            ret = pjp.proceed();
            String retData = "null";
            if(ret != null) {
            	retData = ret.toString();
            }
            logger.info("- Result Data  : {}", retData);
            logger.info("- End Service  : {}.{}", className, methodName);
            logger.info("--------------------------------------------------------------------------------");
        } catch (Throwable ex) {
            logger.error("! Exception Error : {}", ex.getMessage());
            logger.info("- End Service  : {}.{}", className, methodName);
            logger.info("--------------------------------------------------------------------------------");
            
            throw ex;
        }

        return ret;
    }

}
