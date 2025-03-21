package team.rode.supplymanagerrestapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.rode.supplymanagerrestapi.DTO.request.DeliveryItemRequestDto;
import team.rode.supplymanagerrestapi.DTO.request.DeliveryRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.DeliveryResponseDto;
import team.rode.supplymanagerrestapi.models.Delivery;
import team.rode.supplymanagerrestapi.models.Product;
import team.rode.supplymanagerrestapi.models.Supplier;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;
import team.rode.supplymanagerrestapi.repositories.DeliveryRepository;
import team.rode.supplymanagerrestapi.util.DtoConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DtoConverter dtoConverter;

    @Mock
    private EntityRetrievalService entityRetrievalService;

    @InjectMocks
    private DeliveryService deliveryService;

    private DeliveryRequestDto deliveryRequestDto;

    @BeforeEach
    void setUp() {
        deliveryRequestDto = new DeliveryRequestDto();
        deliveryRequestDto.setSupplierId(1L);
        deliveryRequestDto.setDate(LocalDate.of(2025, 3, 15));

        DeliveryItemRequestDto itemDto = new DeliveryItemRequestDto();
        itemDto.setProductId(1L);
        itemDto.setQuantity(10);
        List<DeliveryItemRequestDto> items = new ArrayList<>();
        items.add(itemDto);
        deliveryRequestDto.setDeliveryItemList(items);
    }

    @Test
    void testAddDelivery_Success() {
        Delivery delivery = new Delivery();
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Supplier 1");

        SupplierProductPrice spp = new SupplierProductPrice();
        spp.setPrice(BigDecimal.TEN);

        when(entityRetrievalService.getSupplierById(1L)).thenReturn(supplier);
        when(entityRetrievalService.getSupplierProductPrice(1L, 1L, deliveryRequestDto.getDate())).thenReturn(spp);
        when(entityRetrievalService.getProductById(1L)).thenReturn(new Product());

        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> {
            Delivery d = invocation.getArgument(0);
            d.setId(100L);
            return d;
        });
        DeliveryResponseDto responseDto = new DeliveryResponseDto();
        responseDto.setId(100L);
        when(dtoConverter.convertToDto(any(Delivery.class), eq(DeliveryResponseDto.class)))
                .thenReturn(responseDto);

        DeliveryResponseDto result = deliveryService.addDelivery(deliveryRequestDto);
        assertNotNull(result);
        assertEquals(100L, result.getId());
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
    }

    @Test
    void testDeleteDelivery() {
        Delivery delivery = new Delivery();
        delivery.setId(100L);
        when(entityRetrievalService.getDeliveryById(100L)).thenReturn(delivery);

        deliveryService.deleteDelivery(100L);
        verify(deliveryRepository, times(1)).delete(delivery);
    }
}
