package ecom_inventory_service.controller;

import ecom_inventory_service.model.Inventory;
import ecom_inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /*    @GetMapping("/{productId}")
        public String checkInventory(@PathVariable String productId) {
            // Logic to check inventory for the given productId
            logger.info("Checking inventory for product: " + productId);
            return productId.equals("1") ? "IN_STOCK" : "OUT_OF_STOCK";
        }
     */
    @GetMapping("/{productId}")
    public Inventory checkInventory(@PathVariable Long productId) {
        logger.info("Checking inventory for product id {}", productId);
//        try {
//            Thread.sleep(15000); // Simulate a delay of 15 seconds
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return inventoryService.checkProduct(productId);
    }

    @PostMapping
    public String addProduct(@RequestBody Inventory inventory) {
        logger.info("Adding inventory for product id {}", inventory.getProductId());
        return inventoryService.addProduct(inventory);
    }

    @PutMapping("/{productId}")
    public String updateProduct(@RequestBody Inventory inventory) {
        logger.info("Updating inventory for product id {}", inventory.getProductId());
        return inventoryService.updateProduct(inventory);
    }


}
