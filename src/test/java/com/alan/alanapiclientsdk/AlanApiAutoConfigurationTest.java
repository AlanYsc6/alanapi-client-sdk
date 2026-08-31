package com.alan.alanapiclientsdk;

import com.alan.alanapiclientsdk.client.AlanApiClient;
import com.alan.alanapiclientsdk.config.AlanApiAutoConfiguration;
import com.alan.alanapiclientsdk.config.AlanApiClientConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 自动配置测试：验证配置了 alan.api.access-key 才会装配 AlanApiClient
 *
 * @author ALan
 * @date 2026/8/31 23:09
 *
 */
class AlanApiAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(AlanApiAutoConfiguration.class));

    @Test
    void shouldNotCreateClientWhenAccessKeyMissing() {
        runner.run(context ->
                assertThat(context).doesNotHaveBean(AlanApiClient.class));
    }

    @Test
    void shouldCreateClientWhenAccessKeyConfigured() {
        runner.withPropertyValues(
                        "alan.api.access-key=test-ak",
                        "alan.api.secret-key=test-sk",
                        "alan.api.host=http://localhost:8123/api")
                .run(context -> {
                    assertThat(context).hasSingleBean(AlanApiClient.class);
                    assertThat(context).hasSingleBean(AlanApiClientConfig.class);
                });
    }

}
