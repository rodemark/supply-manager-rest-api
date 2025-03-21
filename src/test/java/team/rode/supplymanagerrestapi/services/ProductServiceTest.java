package team.rode.supplymanagerrestapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.rode.supplymanagerrestapi.DTO.request.ProductRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.ProductResponseDto;
import team.rode.supplymanagerrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.supplymanagerrestapi.models.Product;
import team.rode.supplymanagerrestapi.repositories.ProductRepository;
import team.rode.supplymanagerrestapi.util.DtoConverter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DtoConverter dtoConverter;

    @Mock
    private EntityRetrievalService entityRetrievalService;

    @InjectMocks
    private ProductService productService;

    @Test
    void testAddProduct_Duplicate() {
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setName("Test Product");
        requestDto.setDescription("Test Description");
        requestDto.setUnitOfMeasurement(null);

        when(productRepository.existsSimilarProduct(anyString(), anyString(), any()))
                .thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> productService.addProduct(requestDto));
        assertEquals("A Product with such data already exists", exception.getMessage());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testAddProduct_Success() {
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setName("Test Product");
        requestDto.setDescription("Test Description");
        requestDto.setUnitOfMeasurement(null);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Test Product");

        when(productRepository.existsSimilarProduct(anyString(), anyString(), any()))
                .thenReturn(false);
        when(dtoConverter.convertToEntity(requestDto, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(dtoConverter.convertToDto(product, ProductResponseDto.class)).thenReturn(responseDto);

        ProductResponseDto result = productService.addProduct(requestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product2");

        List<Product> products = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(products);
        when(dtoConverter.convertToDto(any(Product.class), eq(ProductResponseDto.class)))
                .thenAnswer(invocation -> {
                    Product p = invocation.getArgument(0);
                    ProductResponseDto dto = new ProductResponseDto();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    return dto;
                });

        List<ProductResponseDto> result = productService.getProducts();
        assertEquals(2, result.size());
    }
}
