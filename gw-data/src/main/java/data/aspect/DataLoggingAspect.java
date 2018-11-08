package data.aspect;

import com.fasterxml.jackson.core.type.TypeReference;
import data.domain.EntityModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Aspect
@Configuration
@Configurable
public class DataLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(DataLoggingAspect.class);

    @Around(value="data.aspect.CommonJoinPointConfig.dataService()")
    public Object processModelDataInconsistenciesAspectMethodBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Class source = joinPoint.getSourceLocation().getWithinType();

        resetLogger(joinPoint.getSignature().getDeclaringType());
        logger.info("[Entering " + methodName + " from " + source + "]");
        Object j = joinPoint.proceed();
        resetLogger(joinPoint.getSignature().getDeclaringType());
        logger.info("[Leaving " + methodName + " from " + source + "]");

        return j;
    }

    @Around(value="data.aspect.CommonJoinPointConfig.parse()")
    public Object parseLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        resetLogger(joinPoint.getSignature().getDeclaringType());
        logger.info("[Entering " + methodName + "]");
        Object j = joinPoint.proceed();
        logger.info(j.getClass().getName());
        if(j instanceof List<?>){
            List<?> list = (List<?>)j;
            if(list.size() > 0){
                Object k = list.get(0);
                if(k instanceof EntityModel){
                    List<EntityModel> entityModels = (List<EntityModel>)list;
                    for(EntityModel e : entityModels){
                        logger.info("[Parsing EntityModel " + e.getSimpleClassName() + "]");
                    }
                }
            }
        }
        resetLogger(joinPoint.getSignature().getDeclaringType());
        logger.info("[Leaving " + methodName + "]");

        return j;
    }

    private void resetLogger(Class clazz){
        logger = LoggerFactory.getLogger(clazz);
    }


}