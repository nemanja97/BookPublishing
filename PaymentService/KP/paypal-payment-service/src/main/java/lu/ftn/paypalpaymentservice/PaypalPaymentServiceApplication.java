package lu.ftn.paypalpaymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaypalPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalPaymentServiceApplication.class, args);
	}

}
