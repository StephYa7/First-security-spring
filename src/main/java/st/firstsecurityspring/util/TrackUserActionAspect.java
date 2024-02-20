package st.firstsecurityspring.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrackUserActionAspect {


    @Pointcut("@annotation(TrackUserAction)")
    public void trackUserAction() {
    }

    @AfterReturning(value = "trackUserAction()")
    public void log(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println("User action logged - Method: " + methodName + ", User: " + username);
    }

}