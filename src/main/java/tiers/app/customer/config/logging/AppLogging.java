package tiers.app.customer.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppLogging {

    Logger log = LoggerFactory.getLogger(AppLogging.class);

    @Pointcut(value = "execution(* tiers.app.customer.*.*.*(..))")
    public void logPointcut(){

    }

    @Around("logPointcut()")
    public Object appLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        ObjectMapper mapper = new ObjectMapper();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getClass().getName();
        Object[] array = proceedingJoinPoint.getArgs();
        log.info("Method Invoked "+className+" : "+methodName+" () "+" Arguments  :  "+mapper.writeValueAsString(array));
        Object object = proceedingJoinPoint.proceed();
        log.info(className+" : "+methodName+" () "+" Response  :  "+mapper.writeValueAsString(object));
        return object;

    }

}
