package com.alan.alanapiclientsdk.config;

import com.alan.alanapiclientsdk.client.AlanApiClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置：引入本 SDK 的应用只要在 application.yml 中配置了
 * alan.api.access-key，就会自动创建 AlanApiClient Bean，直接注入即可使用
 *
 * @author ALan
 * @date 2026/8/31 23:09
 *
 */
@Configuration
@EnableConfigurationProperties(AlanApiClientConfig.class)
@ConditionalOnProperty(prefix = "alan.api", name = "access-key")
public class AlanApiAutoConfiguration {

    @Bean
    public AlanApiClient alanApiClient(AlanApiClientConfig config) {
        return new AlanApiClient(config.getAccessKey(), config.getSecretKey(), config.getHost());
    }

}
