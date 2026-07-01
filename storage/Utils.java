
import java.lang.reflect.Field;

public class Utils {

    public static long getObjectSize(Object obj) {
        long size = 0;

        // If the object is null, return size 0
        if (obj == null) {
            return size;
        }

        // Get the class of the object
        Class<?> classObj = obj.getClass();

        // Iterate through all declared fields
        for (Field field : classObj.getDeclaredFields()) {
            // Determine the size of each field based on its type
            if (field.getType() == byte.class) {
                size += Byte.BYTES;
            } else if (field.getType() == short.class) {
                size += Short.BYTES;
            } else if (field.getType() == int.class) {
                size += Integer.BYTES;
            } else if (field.getType() == long.class) {
                size += Long.BYTES;
            } else if (field.getType() == float.class) {
                size += Float.BYTES;
            } else if (field.getType() == double.class) {
                size += Double.BYTES;
            } else if (field.getType() == char.class) {
                size += Character.BYTES;
            } else if (field.getType() == boolean.class) {
                size += 1; // Boolean can be 1 byte
            } else {
                // For reference types, we assume a fixed size, e.g., 4 bytes for 32-bit JVM or 8 bytes for 64-bit JVM
                size += Long.BYTES; // Approximation
            }
        }

        return size;
    }
}
