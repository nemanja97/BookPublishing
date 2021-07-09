package lu.ftn.bankpaymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankPaymentServiceApplication.class, args);
	}

}
