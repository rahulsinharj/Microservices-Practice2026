package ecom_inventory_service.service;

import ecom_inventory_service.model.Inventory;
import ecom_inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory checkProduct(Long productId) {
        Optional<Inventory> inv = inventoryRepository.findById(productId);
        return inv.orElse(null);
    }

    public String addProduct(Inventory inventory) {
        inventoryRepository.save(inventory);
        return "Product added successfully";
    }

    public String updateProduct(Inventory inventory) {
        inventoryRepository.save(inventory);
        return "Product updated successfully";
    }
}
