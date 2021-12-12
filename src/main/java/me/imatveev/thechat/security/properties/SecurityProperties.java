package me.imatveev.thechat.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private String secretKey;
    private int expiredAtMinutes;
    private List<String> allowedEndpoints;
}
