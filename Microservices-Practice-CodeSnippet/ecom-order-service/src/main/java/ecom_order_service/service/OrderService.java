package ecom_order_service.service;

import ecom_order_service.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    Logger logger = LoggerFactory.getLogger(OrderController.class);

    public String placeOrder(String productId) {
        // Logic to place an order for the given productId - Call inventory service to check stock.
        logger.info("Placing order for product: " + productId);
        return "Order placed successfully!";
    }
}
