package ecom_order_service.service;

import ecom_order_service.client.InventoryClient;
import ecom_order_service.controller.OrderController;
import ecom_order_service.dto.Inventory;
import ecom_order_service.exceptions.MyCustomRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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

    @Autowired
    private InventoryClient inventoryClient;

    //==============: Call the inventory service using 'RestTemplate' to check if the product is in stock before placing an order.
    public String placeOrder(Long productId) {

        String inventoryResponse = restTemplate.getForObject("http://localhost:8081/inventory/" + productId, String.class);

        // Logic to place an order for the given productId - Call inventory service to check stock.
        logger.info("Placing order for product: " + productId);
        return inventoryResponse.equals("IN_STOCK")
                ? "Order placed for product: " + productId
                : "Product: " + productId + " is out of stock";
    }


    // Refer for more info: https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#_exchange

    //==============: Call the inventory service using 'RestClient' to check if the product is in stock before placing an order.
    public String placeOrder2(Long productId) {

        // Without ResponseEntity return type.
/*        String inventoryResponse = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .body(String.class);
*/

        // With ResponseEntity return type, and without error handling for client errors.
/*        ResponseEntity<Inventory> inventoryResponse = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .toEntity(Inventory.class);
*/
        // With ResponseEntity return type, and with error handling for 4xx client errors, and logging the request and response.
        ResponseEntity<Inventory> inventoryResponse = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    logger.error(request.toString());
                    throw new MyCustomRuntimeException(response.getStatusCode(), response.getHeaders());
                }))
                .toEntity(Inventory.class);

        if (inventoryResponse.getBody() == null || inventoryResponse.getBody().getQuantity() <= 0) {
            logger.info("Product: {} not found in inventory", productId);
            return "Product: " + productId + " is out of stock";
        }

        logger.info("Placing order for product:{}, quantity: {}, and Status Code: {}", productId, inventoryResponse.getBody().getQuantity(), inventoryResponse.getStatusCode());
        updateInventory(inventoryResponse.getBody());
        return "Order placed for product: " + productId;
    }

    // Update the inventory after placing an order using 'RestClient'.
    private void updateInventory(Inventory inventory) {
        logger.info("Updating inventory for product: {}, Current quantity: {}", inventory.getProductId(), inventory.getQuantity());

        inventory.setQuantity(inventory.getQuantity() - 1);
        restClient.post()
                .uri("http://localhost:8081/inventory")
                .body(inventory)
                .retrieve()
                .toBodilessEntity();
    }

    // =============: Call the inventory service using 'Feign client' to check if the product is in stock before placing an order.
    public String placeOrder3(Long productId) {
        // Use the Feign client to call the inventory service.
        Inventory inventory = inventoryClient.getInventory(productId);
        if (inventory == null || inventory.getQuantity() <= 0) {
            logger.info("Product: {} not found in inventory", productId);
            return "Product: " + productId + " is out of stock";
        }

        logger.info("Placing order for product:{}, quantity: {}", productId, inventory.getQuantity());
        updateProductInventory(inventory);
        return "Order placed for product: " + productId;
    }

    // Update the inventory after placing an order using 'Feign client'.
    private void updateProductInventory(Inventory inventory) {
        logger.info("Updating Product Inventory: {}, Current quantity: {}", inventory.getProductId(), inventory.getQuantity());

        inventory.setQuantity(inventory.getQuantity() - 1);
        inventoryClient.updateProductInventory(inventory);
    }

}
