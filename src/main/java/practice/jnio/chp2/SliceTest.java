package practice.jnio.chp2;

import java.lang.reflect.Method;

public class SliceTest {
    public static void main(String[] args) {
        testClassIdentity();
    }

    public static void testClassIdentity() {
        String classDataRootPath = "C:\\";
        FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDataRootPath);
        FileSystemClassLoader fscl2 = new FileSystemClassLoader(classDataRootPath);
        String className = "practice.jnio.chp2.Sample";
        try {
            Class<?> class1 = fscl1.loadClass(className);
            Object obj1 = class1.newInstance();
            Class<?> class2 = fscl2.loadClass(className);
            Object obj2 = class2.newInstance();
            Method setSampleMethod = class1.getMethod("setSample", Object.class);
            setSampleMethod.invoke(obj1, obj2);
            System.out.println("fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
