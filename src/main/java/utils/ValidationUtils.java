package utils;

import java.util.Collection;
import java.util.Map;

public class ValidationUtils {
    public static void validateId(Long id, String logName) {
        validateNotNull(id, logName);
        if (id < 0) throw new IllegalArgumentException("id must be 0 or positive.");
    }

    public static void validateString(String str, String logName) {
        validateNotNull(str, logName);
        if (str.trim().isEmpty())
            throw new IllegalArgumentException(logName + " cannot be empty.");
    }

    public static void validateString(String str, int minLength, String logName) {
        validateString(str, logName);
        if (str.trim().length() < minLength)
            throw new IllegalArgumentException(logName + " must have at least " + minLength + " characters.");
    }

    public static void validateNotNull(Object obj, String logName) {
        if (obj == null)
            throw new NullPointerException(logName + " cannot be null.");
    }

    public static void validateNotEmpty(Collection<?> collection, String logName) {
        validateNotNull(collection, logName);
        if (collection.isEmpty())
            throw new IllegalArgumentException(logName + " cannot be empty.");
    }
}
