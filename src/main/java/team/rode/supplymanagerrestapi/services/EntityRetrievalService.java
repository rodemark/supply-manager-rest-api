package team.rode.supplymanagerrestapi.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.supplymanagerrestapi.exceptionHandling.exceptions.ResourceNotFoundException;
import team.rode.supplymanagerrestapi.models.Delivery;
import team.rode.supplymanagerrestapi.models.Product;
import team.rode.supplymanagerrestapi.models.Supplier;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;
import team.rode.supplymanagerrestapi.repositories.DeliveryRepository;
import team.rode.supplymanagerrestapi.repositories.ProductRepository;
import team.rode.supplymanagerrestapi.repositories.SupplierProductPriceRepository;
import team.rode.supplymanagerrestapi.repositories.SupplierRepository;

import java.time.LocalDate;

@Service
@Slf4j
public class EntityRetrievalService {
    private final SupplierRepository supplierRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductRepository productRepository;
    private final SupplierProductPriceRepository supplierProductPriceRepository;

    @Autowired
    public EntityRetrievalService(SupplierRepository supplierRepository, DeliveryRepository deliveryRepository,
                                  ProductRepository productRepository, SupplierProductPriceRepository supplierProductPriceRepository) {
        this.supplierRepository = supplierRepository;
        this.deliveryRepository = deliveryRepository;
        this.productRepository = productRepository;
        this.supplierProductPriceRepository = supplierProductPriceRepository;
    }

    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> {
                    log.error("Supplier not found with id: {}", supplierId);
                    return new ResourceNotFoundException("Supplier not found with id: " + supplierId);
                });
    }

    public Delivery getDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> {
                    log.error("Delivery not found with id: {}", deliveryId);
                    return new ResourceNotFoundException("Delivery not found with id: " + deliveryId);
                });
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", productId);
                    return new ResourceNotFoundException("Product not found with id: " + productId);
                });
    }

    public SupplierProductPrice getSupplierProductPriceById(Long id) {
        return supplierProductPriceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("SupplierProductPrice not found with id: {}", id);
                    return new ResourceNotFoundException("SupplierProductPrice not found with id: " + id);
                });
    }

    public SupplierProductPrice getSupplierProductPrice(Long supplierId, Long productId, LocalDate date) {
        return supplierProductPriceRepository.findCurrentPrice(supplierId, productId, date)
                .orElseThrow(() -> {
                    log.error("SupplierProductPrice not found with supplierId: {}, productId: {}, date: {}",
                            supplierId, productId, date);

                    return new ResourceNotFoundException("SupplierProductPrice not found with supplierId: "
                            + supplierId + " productId: " + productId + " date: " + date);
                });
    }
}