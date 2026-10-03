package rahul_microservices.department_service.config;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//import org.springframework.web.client.support.RestClientAdapter;
//import org.springframework.web.service.invoker.HttpServiceProxyFactory;
//import rahul_microservices.department_service.client.EmployeeClient;

@Configuration
public class RestClientConfig {

//    @Bean
//    EmployeeClient employeeClient(LoadBalancerClient loadBalancerClient,
//                                  @Value("${employee.service.name:employee-service}") String serviceId) {
//        // Try to choose a running instance from the service registry
//        String baseUrl = loadBalancerClient.choose(serviceId).getUri().toString();
//
//        RestClient restClient = RestClient.builder()
//                .baseUrl(baseUrl)
//                .build();
//
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(RestClientAdapter.create(restClient))
//                .build();
//
//        return factory.createClient(EmployeeClient.class);
//    }

/*    @Bean
    EmployeeClient employeeClient(LoadBalancerClient loadBalancerClient,
                                  @Value("${employee.service.name:employee-service}") String serviceId) {
        // Try to choose a running instance from the service registry
        String baseUrl = loadBalancerClient.choose(serviceId).getUri().toString();

        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(EmployeeClient.class);
    }*/

    // Do not expose a global @LoadBalanced RestClient.Builder here – that can be picked up
    // by other auto-config and interfere with Eureka registration. Instead resolve the
    // employee service instance with LoadBalancerClient when creating the client and
    // fall back to a configured localhost URL for development.
/*    @Bean
    EmployeeClient employeeClient(LoadBalancerClient loadBalancerClient,
                                  @Value("${employee.service.name:employee-service}") String serviceId,
                                  @Value("${employee.service.url:http://localhost:8082}") String fallbackUrl) {

        // Try to choose a running instance from the service registry
        ServiceInstance instance = null;
        try {
            instance = loadBalancerClient.choose(serviceId);
        } catch (Exception ex) {
            // ignore and rely on fallback
        }

        String baseUrl = (instance != null) ? instance.getUri().toString() : fallbackUrl;
        System.out.println("############### instance : " + instance);
        if (instance != null) System.out.println("############### instance.getUri : " + instance.getUri().toString());
        System.out.println("############### baseUrl : " + baseUrl);

        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(EmployeeClient.class);
    }*/

/*    @Bean
    EmployeeClient employeeClient() {

        RestClient restClient =
                RestClient.builder()
                        .baseUrl("http://EMPLOYEE-SERVICE")
                        .build();

        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory
                        .builderFor(
                                RestClientAdapter.create(restClient)
                        )
                        .build();

        return factory.createClient(EmployeeClient.class);
    }*/

 /*   @Bean
    @LoadBalanced
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    EmployeeClient employeeClient(RestClient.Builder restClientBuilder, @Value("${employee.service.url}") String baseUrl) {

        RestClient restClient = restClientBuilder.baseUrl(baseUrl).build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(EmployeeClient.class);
}
  */

}