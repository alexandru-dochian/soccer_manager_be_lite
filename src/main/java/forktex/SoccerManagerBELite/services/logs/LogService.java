package forktex.SoccerManagerBELite.services.logs;

import forktex.SoccerManagerBELite.enums.LogCategoryType;
import forktex.SoccerManagerBELite.enums.LogPurposeType;
import forktex.SoccerManagerBELite.repositories.entities.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import forktex.SoccerManagerBELite.repositories.LogRepository;

@Slf4j
@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    @Async
    public void saveLog(LogCategoryType logCategoryType, LogPurposeType logPurposeType,
                        Long userId, String details, String payload) {
        final Log toBeLogged = new Log()
                .setUserId(userId)
                .setLogCategory(logCategoryType.name())
                .setLogPurpose(logPurposeType.name())
                .setDetails(details)
                .setPayload(payload == null ? "" : payload);

        log.debug("{} | {}", toBeLogged.getDetails(), toBeLogged.getPayload());
        logRepository.save(toBeLogged);
    }
}
