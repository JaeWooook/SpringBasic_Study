package springstudy.springtest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component//컴포넌트 스캔을 사용해도된다.
public class TimeTraceAop {//이것을 스프링 빈에 등록해줘야한다.

    @Around("execution(* springstudy.springtest..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: "+joinPoint.toString());
        try{
//            Object result = joinPoint.proceed();
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("END: "+joinPoint.toString() +" "+ timeMs+"ms");
        }


    }
}
