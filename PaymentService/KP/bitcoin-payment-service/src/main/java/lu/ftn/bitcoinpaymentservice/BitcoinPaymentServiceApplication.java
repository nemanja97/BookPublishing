package lu.ftn.bitcoinpaymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BitcoinPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinPaymentServiceApplication.class, args);
	}

}
