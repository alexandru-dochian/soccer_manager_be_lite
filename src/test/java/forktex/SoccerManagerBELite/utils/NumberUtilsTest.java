package forktex.SoccerManagerBELite.utils;

import forktex.SoccerManagerBELite.enums.LogCategoryType;
import forktex.SoccerManagerBELite.enums.LogPurposeType;
import forktex.SoccerManagerBELite.enums.UserType;
import forktex.SoccerManagerBELite.services.logs.LogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
public class NumberUtilsTest {
    @Autowired
    private LogService logService;
    @Test
    public void testAgeGenerator() {
        logService.saveLog(LogCategoryType.TEST,
                LogPurposeType.START,
                UserType.UNKNOWN.getValue(),
                "Testing NumberUtilsTest.testAgeGenerator()!",
                "");

        boolean success = true;
        final int minAge = 18;
        final int maxAge = 40;
        for (int i = 0; i < 1000; i++) {
            final int generatedAge = NumberUtils.getRandomAge();

            if (generatedAge < minAge || generatedAge > maxAge) {
                success = false;
                break;
            }
        }

        logService.saveLog(LogCategoryType.TEST,
                LogPurposeType.END,
                UserType.UNKNOWN.getValue(),
                "Ended NumberUtilsTest.testAgeGenerator()!",
                "");
        Assertions.assertTrue(success);
    }
}
