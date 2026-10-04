package ecom_inventory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcomInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomInventoryServiceApplication.class, args);
		System.out.println("EcomInventoryService is running on port 8081");
	}

}
