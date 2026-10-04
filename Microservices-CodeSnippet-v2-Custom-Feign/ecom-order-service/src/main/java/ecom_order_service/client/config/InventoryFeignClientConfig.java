package ecom_order_service.client.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryFeignClientConfig {

    @Bean
    public Logger.Level invFeignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
