package com.bootlabs.springsecuritypaseto.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties specific to application.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */

@Data
@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final BruteForceAttack bruteForceAttack = new BruteForceAttack();
    private final PasswordHistory passwordHistory = new PasswordHistory();


    @Getter
    @Setter
    public static class BruteForceAttack{
        private boolean enabled;
        private int maxLoginAttempts;
        private int lockTimeDuration;
    }
    @Getter
    @Setter
    public static class PasswordHistory{
        private boolean enabled;
        private int maxDayRenew;
    }

}
