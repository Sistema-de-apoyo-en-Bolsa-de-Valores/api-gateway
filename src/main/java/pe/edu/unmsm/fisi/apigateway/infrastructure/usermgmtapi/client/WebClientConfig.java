package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${user-mgmt-api.base-url}")
    private String userMgmtApiBaseUrl;

    @Value("${user-mgmt-api.path}")
    private String userMgmtApiPath;

    @Bean
    public WebClient userMgmtApiWebClient() {
        return WebClient.builder()
                .baseUrl(userMgmtApiBaseUrl.concat(userMgmtApiPath))
                .build();
    }
}