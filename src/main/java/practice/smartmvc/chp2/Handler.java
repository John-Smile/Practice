package practice.smartmvc.chp2;

import java.lang.reflect.Method;

public class Handler {
    private Class<?> controllerClass;
    private Method actionMethod;
    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.actionMethod = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
