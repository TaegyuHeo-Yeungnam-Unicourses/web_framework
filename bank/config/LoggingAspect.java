package bank.config;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(public * bank.service.AccountCreateService.*(..)) || "
            + "execution(public * bank.service.TransactionService.*(..))")
    public void accountOperations() { }

    @Before("accountOperations()")
    public void before(JoinPoint joinPoint) {
        System.out.printf("%s.%s : 호출 시간 = %s%n",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(), LocalDateTime.now());
    }
    @AfterReturning(pointcut = "accountOperations()", returning = "retVal", argNames = "retVal")
    public void afterReturning(Object retVal) {
        System.out.println("return 값 = " + retVal);
    }
}
