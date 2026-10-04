package ecom_order_service.service;

import ecom_order_service.client.InventoryClient;
import ecom_order_service.controller.OrderController;
import ecom_order_service.dto.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private InventoryClient inventoryClient;

    // =============: Call the inventory service using 'Feign client' to check if the product is in stock before placing an order.
    public String placeOrder(Long productId) {
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
