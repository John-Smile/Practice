package practice.smartmvc.chp2;

import org.apache.commons.lang.ArrayUtils;

public class ArrayUtil {
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
