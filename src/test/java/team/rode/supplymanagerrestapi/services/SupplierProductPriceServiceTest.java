package team.rode.supplymanagerrestapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.rode.supplymanagerrestapi.DTO.request.SupplierProductPriceRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.SupplierProductPriceResponseDto;
import team.rode.supplymanagerrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.supplymanagerrestapi.models.Product;
import team.rode.supplymanagerrestapi.models.Supplier;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;
import team.rode.supplymanagerrestapi.repositories.SupplierProductPriceRepository;
import team.rode.supplymanagerrestapi.util.DtoConverter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierProductPriceServiceTest {

    @Mock
    private SupplierProductPriceRepository supplierProductPriceRepository;

    @Mock
    private DtoConverter dtoConverter;

    @Mock
    private EntityRetrievalService entityRetrievalService;

    @InjectMocks
    private SupplierProductPriceService supplierProductPriceService;

    @Test
    void testAddSupplierProductPrice_Duplicate() {
        SupplierProductPriceRequestDto requestDto = new SupplierProductPriceRequestDto();
        requestDto.setSupplierId(1L);
        requestDto.setProductId(1L);
        requestDto.setStartDate(LocalDate.of(2025, 3, 1));
        requestDto.setEndDate(LocalDate.of(2025, 3, 31));
        requestDto.setPrice(BigDecimal.TEN);

        when(supplierProductPriceRepository.existsSimilarSupplierProductPrice(
                anyLong(), anyLong(), any(), any(), any()))
                .thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> supplierProductPriceService.addSupplierProductPrice(requestDto));
        assertEquals("A SupplierProductPrice with such data already exists", exception.getMessage());
        verify(supplierProductPriceRepository, never()).save(any(SupplierProductPrice.class));
    }

    @Test
    void testAddSupplierProductPrice_Success() {
        SupplierProductPriceRequestDto requestDto = new SupplierProductPriceRequestDto();
        requestDto.setSupplierId(1L);
        requestDto.setProductId(1L);
        requestDto.setStartDate(LocalDate.of(2025, 3, 1));
        requestDto.setEndDate(LocalDate.of(2025, 3, 31));
        requestDto.setPrice(BigDecimal.TEN);

        SupplierProductPrice spp = new SupplierProductPrice();
        spp.setId(1L);
        spp.setPrice(BigDecimal.TEN);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        Product product = new Product();
        product.setId(1L);

        when(supplierProductPriceRepository.existsSimilarSupplierProductPrice(
                anyLong(), anyLong(), any(), any(), any())).thenReturn(false);
        when(entityRetrievalService.getSupplierById(1L)).thenReturn(supplier);
        when(entityRetrievalService.getProductById(1L)).thenReturn(product);
        when(supplierProductPriceRepository.save(any(SupplierProductPrice.class))).thenReturn(spp);
        SupplierProductPriceResponseDto responseDto = new SupplierProductPriceResponseDto();
        responseDto.setId(1L);
        when(dtoConverter.convertToDto(spp, SupplierProductPriceResponseDto.class)).thenReturn(responseDto);

        SupplierProductPriceResponseDto result = supplierProductPriceService.addSupplierProductPrice(requestDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
