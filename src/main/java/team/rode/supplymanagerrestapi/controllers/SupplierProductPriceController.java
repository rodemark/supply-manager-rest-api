package team.rode.supplymanagerrestapi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.rode.supplymanagerrestapi.DTO.request.SupplierProductPriceRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.SupplierProductPriceResponseDto;
import team.rode.supplymanagerrestapi.services.SupplierProductPriceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier-product-prices")
public class SupplierProductPriceController {

    private final SupplierProductPriceService supplierProductPriceService;

    @Autowired
    public SupplierProductPriceController(SupplierProductPriceService supplierProductPriceService) {
        this.supplierProductPriceService = supplierProductPriceService;
    }

    @GetMapping
    public List<SupplierProductPriceResponseDto> getSupplierProductPrices() {
        return supplierProductPriceService.getSupplierProductPrices();
    }

    @PostMapping
    public SupplierProductPriceResponseDto addSupplierProductPrice(@Valid @RequestBody SupplierProductPriceRequestDto dto) {
        return supplierProductPriceService.addSupplierProductPrice(dto);
    }

    @PutMapping("/{id}")
    public SupplierProductPriceResponseDto editSupplierProductPrice(@Valid @RequestBody SupplierProductPriceRequestDto dto,
                                                                    @PathVariable Long id) {
        return supplierProductPriceService.editSupplierProductPrice(dto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplierProductPrice(@PathVariable Long id) {
        supplierProductPriceService.deleteSupplierProductPrice(id);
    }
}
