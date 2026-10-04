package ecom_order_service.client;

import ecom_order_service.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service", url = "http://localhost:8081")
public interface InventoryClient {

    @GetMapping("/inventory/{productId}")
    Inventory getInventory(@PathVariable Long productId);

    @PostMapping("/inventory")
    public String updateProductInventory(@RequestBody Inventory inventory);

}
