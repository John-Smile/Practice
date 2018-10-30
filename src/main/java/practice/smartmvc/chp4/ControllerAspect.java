package practice.smartmvc.chp4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import practice.smartmvc.chp2.Controller;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends  AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        LOGGER.debug("-----------begin-----------");
        LOGGER.debug(String.format("class:%s", cls.getName()));
        LOGGER.debug(String.format("method:%s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) {
        LOGGER.debug(String.format("time:%dms", System.currentTimeMillis() - begin));
        LOGGER.debug("-----------end-----------");
    }
}
