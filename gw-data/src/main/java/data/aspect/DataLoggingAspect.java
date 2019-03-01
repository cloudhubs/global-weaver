package data.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Configurable
public class DataLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(DataLoggingAspect.class);

    @Around(value="data.aspect.CommonJoinPointConfig.controllerInconsistencies()")
    public Object controllerInconsistenciesAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("[Calculating inconsistencies ... ]");
        Object o = joinPoint.proceed();
        resetLogger(joinPoint.getSignature().getDeclaringType());
        logger.info("[Finished processing inconsistencies.]");
        return o;
    }

//    @Around(value="data.aspect.CommonJoinPointConfig.controllerValidation()")
//    public Object controllerValidationAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        logger.info("[Calculating validation points ... ]");
//        Object o = joinPoint.proceed();
//        resetLogger(joinPoint.getSignature().getDeclaringType());
//        logger.info("[Finished processing validation points.]");
//        return o;
//    }
//
//    @Around(value="data.aspect.CommonJoinPointConfig.serviceGetModelData()")
//    public Object sserviceGetModelDataAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        resetLogger(joinPoint.getSignature().getDeclaringType());
//        logger.info("[Retrieving harvester entity data ... ]");
//        Object o = joinPoint.proceed();
//        HarvesterData harvest = (HarvesterData)o;
//        for(LocalWeaverResult lwr : harvest.getData()){
//            logger.info("[Retrieved harvester entity data for module " + lwr.getModuleName() + "]");
//        }
//        logger.info("[Finished retrieving harvester entity data.]");
//        return o;
//    }
//
//    @Around(value="data.aspect.CommonJoinPointConfig.serviceGetBytecodeData()")
//    public Object serviceGetBytecodeDataAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        resetLogger(joinPoint.getSignature().getDeclaringType());
//        logger.info("[Retrieving harvester bytecode data ... ]");
//        Object o = joinPoint.proceed();
//        HarvesterData harvest = (HarvesterData)o;
//        for(LocalWeaverResult lwr : harvest.getData()){
//            logger.info("[Retrieved harvester bytecode data for module " + lwr.getModuleName() + "]");
//        }
//        logger.info("[Finished retrieving harvester bytecode data.]");
//        return o;
//    }
//
//    @Around(value="data.aspect.CommonJoinPointConfig.serviceAggregateModelData()")
//    public Object serviceAggregateModelDataAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        resetLogger(joinPoint.getSignature().getDeclaringType());
//        logger.info("[Aggregating entity data ... ]");
//        Object o = joinPoint.proceed();
//        List<EntityModel> models = (List<EntityModel>)o;
//        for(EntityModel model : models){
//            logger.info("[Aggregating entity " + model.getClassName() + "]");
//        }
//        logger.info("[Finished aggregating entity data.]");
//        return o;
//    }

    private void resetLogger(Class clazz){
        logger = LoggerFactory.getLogger(clazz);
    }


}