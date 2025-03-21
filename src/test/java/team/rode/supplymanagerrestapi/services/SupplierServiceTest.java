package team.rode.supplymanagerrestapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.rode.supplymanagerrestapi.DTO.request.SupplierRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.SupplierResponseDto;
import team.rode.supplymanagerrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.supplymanagerrestapi.models.Supplier;
import team.rode.supplymanagerrestapi.repositories.SupplierRepository;
import team.rode.supplymanagerrestapi.util.DtoConverter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private DtoConverter dtoConverter;

    @Mock
    private EntityRetrievalService entityRetrievalService;

    @InjectMocks
    private SupplierService supplierService;

    @Test
    void testAddSupplier_Duplicate() {
        SupplierRequestDto requestDto = new SupplierRequestDto();
        requestDto.setName("Test Supplier");
        requestDto.setContact("123456");

        when(supplierRepository.existsSimilarSupplier(anyString(), anyString())).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> supplierService.addSupplier(requestDto));
        assertEquals("A Supplier with such data already exists", exception.getMessage());
        verify(supplierRepository, never()).save(any(Supplier.class));
    }

    @Test
    void testAddSupplier_Success() {
        SupplierRequestDto requestDto = new SupplierRequestDto();
        requestDto.setName("Test Supplier");
        requestDto.setContact("123456");

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Test Supplier");
        supplier.setContact("123456");

        SupplierResponseDto responseDto = new SupplierResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Test Supplier");
        responseDto.setContact("123456");

        when(supplierRepository.existsSimilarSupplier(anyString(), anyString())).thenReturn(false);
        when(dtoConverter.convertToEntity(requestDto, Supplier.class)).thenReturn(supplier);
        when(supplierRepository.save(supplier)).thenReturn(supplier);
        when(dtoConverter.convertToDto(supplier, SupplierResponseDto.class)).thenReturn(responseDto);

        SupplierResponseDto result = supplierService.addSupplier(requestDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    void testGetSuppliers() {
        Supplier supplier1 = new Supplier();
        supplier1.setId(1L);
        supplier1.setName("Supplier1");
        Supplier supplier2 = new Supplier();
        supplier2.setId(2L);
        supplier2.setName("Supplier2");

        List<Supplier> suppliers = List.of(supplier1, supplier2);
        when(supplierRepository.findAll()).thenReturn(suppliers);
        when(dtoConverter.convertToDto(any(Supplier.class), eq(SupplierResponseDto.class)))
                .thenAnswer(invocation -> {
                    Supplier s = invocation.getArgument(0);
                    SupplierResponseDto dto = new SupplierResponseDto();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    return dto;
                });

        List<SupplierResponseDto> result = supplierService.getSuppliers();
        assertEquals(2, result.size());
    }
}
