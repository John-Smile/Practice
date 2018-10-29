package practice.smartmvc.chp2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class:" + cls);
        }
        return cls.cast(BEAN_MAP.get(cls));
    }
}
