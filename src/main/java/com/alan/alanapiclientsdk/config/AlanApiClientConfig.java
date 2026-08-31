package com.alan.alanapiclientsdk.config;

import com.alan.alanapiclientsdk.client.AlanApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置类（alanapi-client-sdk 的调用凭证与网关地址）
 *
 * @author ALan
 * @date 2026/8/31 23:09
 *
 */
@Data
@ConfigurationProperties(prefix = "alan.api")
public class AlanApiClientConfig {

    /**
     * 开放平台颁发的访问凭证
     */
    private String accessKey;

    /**
     * 密钥，只参与本地签名计算，绝不随请求发送
     */
    private String secretKey;

    /**
     * 网关地址，默认本地接口服务
     */
    private String host = AlanApiClient.DEFAULT_GATEWAY_HOST;

}
