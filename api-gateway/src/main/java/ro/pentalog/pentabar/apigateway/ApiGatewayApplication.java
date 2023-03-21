package ro.pentalog.pentabar.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        // cambio
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
