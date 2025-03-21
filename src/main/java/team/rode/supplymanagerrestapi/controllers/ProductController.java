package team.rode.supplymanagerrestapi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.rode.supplymanagerrestapi.DTO.request.ProductRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.ProductResponseDto;
import team.rode.supplymanagerrestapi.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public ProductResponseDto addProduct(@Valid  @RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(productRequestDto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto editProduct(@Valid @RequestBody ProductRequestDto productRequestDto, @PathVariable Long id) {
        return productService.editProduct(productRequestDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
