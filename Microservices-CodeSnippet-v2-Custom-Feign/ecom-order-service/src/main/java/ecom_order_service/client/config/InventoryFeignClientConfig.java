package ecom_order_service.client.config;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class InventoryFeignClientConfig {

    // Set the Feign client logger level to FULL for detailed logging of requests and responses
    @Bean
    public Logger.Level invFeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    // Configure Feign client request options with custom timeouts
    @Bean
    public Request.Options options() {
//        return new Request.Options(3000, 5000);
        return new Request.Options(Duration.ofMillis(3000), Duration.ofMillis(5000), true);
    }

    // Configure Feign client retry behavior
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1L, 2L, 3);
    }

    // Configure a Feign client request interceptor to add custom headers (like Authorization header) to outgoing requests
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Add custom headers to the request
            requestTemplate.header("X-Custom-Header", "CustomHeaderValue");
            requestTemplate.header("X-Correlation-Id", UUID.randomUUID().toString());
            // You can add more headers or modify the request as needed
        };
    }

}
