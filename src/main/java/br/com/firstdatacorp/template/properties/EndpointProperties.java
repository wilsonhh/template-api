package br.com.firstdatacorp.template.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("endpoints")
@Data
public class EndpointProperties {
    private boolean debug;

    private API api1;

    @Data
    public static class API {
        @Value("${base-url}")
        private String baseUrl;

        private String username;

        private String password;
    }
}
