package ecom_order_service.service;

import ecom_order_service.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RestTemplate restTemplate;

    // Call the inventory service using 'RestTemplate' to check if the product is in stock before placing an order.
    public String placeOrder(String productId) {

        String response = restTemplate.getForObject("http://localhost:8081/inventory/" + productId, String.class);

        // Logic to place an order for the given productId - Call inventory service to check stock.
        logger.info("Placing order for product: " + productId);
        return response.equals("IN_STOCK")
                ? "Order placed for product: " + productId
                : "Product: " + productId + " is out of stock";
    }
}
