package ecom_order_service.service;

import ecom_order_service.controller.OrderController;
import ecom_order_service.dto.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestClient restClient;

    // Call the inventory service using 'RestTemplate' to check if the product is in stock before placing an order.
    public String placeOrder(String productId) {

        String response = restTemplate.getForObject("http://localhost:8081/inventory/" + productId, String.class);

        // Logic to place an order for the given productId - Call inventory service to check stock.
        logger.info("Placing order for product: " + productId);
        return response.equals("IN_STOCK")
                ? "Order placed for product: " + productId
                : "Product: " + productId + " is out of stock";
    }

    // Call the inventory service using 'RestClient' to check if the product is in stock before placing an order.
    public String placeOrder2(String productId) {

/*        String response = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .body(String.class);
*/
        ResponseEntity<Inventory> response = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .toEntity(Inventory.class);

        if (response.getBody() == null || response.getBody().getQuantity() <= 0) {
            logger.info("Product: {} not found in inventory", productId);
            return "Product: " + productId + " is out of stock";
        }

        logger.info("Placing order for product:{}, quantity: {}, and Status Code: {}", productId, response.getBody().getQuantity(), response.getStatusCode());
        updateInventory(response.getBody());
        return "Order placed for product: " + productId;
    }

    private void updateInventory(Inventory inventory) {
        // Logic to update inventory after placing an order
        logger.info("Updating inventory for product: " + inventory.getProductId());
        logger.info("Current quantity: " + inventory.getQuantity());
        inventory.setQuantity(inventory.getQuantity() - 1);
        restClient.post()
                .uri("http://localhost:8081/inventory")
                .body(inventory)
                .retrieve()
                .toBodilessEntity();
    }


}
