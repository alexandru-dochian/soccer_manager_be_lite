package forktex.SoccerManagerBELite.utils;

import java.util.Random;

public class NumberUtils {
    public static Integer getRandomAge() {
        final int MIN_AGE = 18;
        final int MAX_AGE = 40;
        return getRandomNumberInRange(MIN_AGE, MAX_AGE + 1);
    }

    public static Double getMarketIncreaseMultiplier() {
        final int MIN_PERCENTAGE = 10;
        final int MAX_PERCENTAGE = 100;
        final double increase = (double) getRandomNumberInRange(MIN_PERCENTAGE, MAX_PERCENTAGE + 1) / 100;
        return 1.0 + increase;
    }

    private static Integer getRandomNumberInRange(Integer from, Integer to) {
        return from + new Random().nextInt(to - from);
    }
}
