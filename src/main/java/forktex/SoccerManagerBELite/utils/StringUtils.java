package forktex.SoccerManagerBELite.utils;

import java.util.UUID;

public class StringUtils {
    public static String generateRandomString() {
        final int DEFAULT_STRING_SIZE = 8;
        return StringUtils.generateRandomString(DEFAULT_STRING_SIZE);
    }

    public static String generateRandomString(Integer stringSize) {
        final int DEFAULT_STRING_SIZE = 8;

        if (stringSize == null || stringSize < 1) {
            stringSize = DEFAULT_STRING_SIZE;
        }

        return UUID.randomUUID().toString().substring(0, stringSize);
    }
}
