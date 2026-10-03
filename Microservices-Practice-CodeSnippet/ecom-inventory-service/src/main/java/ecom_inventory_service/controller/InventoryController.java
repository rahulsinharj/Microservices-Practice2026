package ecom_inventory_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @GetMapping("/{productId}")
    public String checkInventory(@PathVariable String productId) {
        // Logic to check inventory for the given productId
        logger.info("Checking inventory for product: " + productId);
        return productId.equals("1") ? "IN_STOCK" : "OUT_OF_STOCK";
    }
}
