package com.paymentmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PaymentMonitoringSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(PaymentMonitoringSystemApplication.class, args);
	}

}
