package harvester.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Configurable
public class HarvesterServiceAspect {

    @Around(value="harvester.aspect.CommonJoinPointConfig.iotFrameworkAnnotation()")
    public void iotFrameworkAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around");
        Object object = joinPoint.proceed();
        System.out.println("around");
    }
}