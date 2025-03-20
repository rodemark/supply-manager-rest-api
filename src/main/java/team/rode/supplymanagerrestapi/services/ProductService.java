package team.rode.supplymanagerrestapi.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.supplymanagerrestapi.DTO.request.ProductRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.ProductResponseDto;
import team.rode.supplymanagerrestapi.models.Product;
import team.rode.supplymanagerrestapi.repositories.ProductRepository;
import team.rode.supplymanagerrestapi.util.DtoConverter;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final DtoConverter dtoConverter;
    private final EntityRetrievalService entityRetrievalService;

    @Autowired
    public ProductService(ProductRepository productRepository, DtoConverter dtoConverter, EntityRetrievalService entityRetrievalService) {
        this.productRepository = productRepository;
        this.dtoConverter = dtoConverter;
        this.entityRetrievalService = entityRetrievalService;
    }

    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> dtoConverter.convertToDto(product, ProductResponseDto.class))
                .toList();
    }

    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.save(dtoConverter.convertToEntity(productRequestDto, Product.class));
        return dtoConverter.convertToDto(product, ProductResponseDto.class);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = entityRetrievalService.getProductById(productId);
        productRepository.delete(product);
    }

    @Transactional
    public ProductResponseDto editProduct(ProductRequestDto productRequestDto, Long productId) {
        Product product = entityRetrievalService.getProductById(productId);

        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setUnitOfMeasurement(productRequestDto.getUnitOfMeasurement());
        product = productRepository.save(product);

        return dtoConverter.convertToDto(product, ProductResponseDto.class);
    }
}