package com.alan.alanapiclientsdk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author ALan
 * @date 2026/8/31 23:09
 *
 */
@Data
@ComponentScan
@Configuration
@ConfigurationProperties(prefix = "alan.api")
public class AlanApiClientConfig {

    private String accessKey;

    private String SecretKey;

}

