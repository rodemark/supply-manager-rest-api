package team.rode.supplymanagerrestapi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.rode.supplymanagerrestapi.DTO.request.SupplierRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.SupplierResponseDto;
import team.rode.supplymanagerrestapi.services.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping()
    public List<SupplierResponseDto> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @PostMapping()
    public SupplierResponseDto addSupplier(@RequestBody SupplierRequestDto supplierRequestDto) {
        return supplierService.addSupplier(supplierRequestDto);
    }

    @DeleteMapping("/{supplierId}")
    public void deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
    }

    @PatchMapping("/{supplierId}")
    public SupplierResponseDto editSupplier(@RequestBody SupplierRequestDto supplierRequestDto,
                                            @PathVariable Long supplierId) {
        return supplierService.editSupplier(supplierRequestDto, supplierId);
    }
}
