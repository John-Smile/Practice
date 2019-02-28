package practice.experiment;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReturnType {
    public int returnInt() {
        return 0;
    }

    public Integer returnInteger() {
        return 0;
    }

    public List returnList() {
        return null;
    }

    public List<String> returnStringList() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        printReturnType(ReturnType.class);
    }

    private static void printReturnType(Class clazz) throws Exception {
        Method[] methods =clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " " + method.getReturnType() + " " + method.getGenericReturnType());
        }
    }
}
