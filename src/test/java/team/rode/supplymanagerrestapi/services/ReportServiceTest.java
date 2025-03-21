package team.rode.supplymanagerrestapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.rode.supplymanagerrestapi.DTO.response.ReportResponseByDeliveryItemDto;
import team.rode.supplymanagerrestapi.models.Delivery;
import team.rode.supplymanagerrestapi.models.DeliveryItem;
import team.rode.supplymanagerrestapi.models.Product;
import team.rode.supplymanagerrestapi.models.Supplier;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;
import team.rode.supplymanagerrestapi.repositories.DeliveryItemRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private DeliveryItemRepository deliveryItemRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void testGetReport_BySupplierIdAndProductId() {
        Delivery delivery = new Delivery();
        delivery.setDate(LocalDate.of(2025, 3, 15));

        Supplier supplier = new Supplier();
        supplier.setName("Supplier A");
        delivery.setSupplier(supplier);

        DeliveryItem di = new DeliveryItem();
        di.setId(1L);
        di.setPrice(BigDecimal.TEN);
        di.setQuantity(5);
        di.setDelivery(delivery);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setUnitOfMeasurement(UnitOfMeasurement.KILOGRAM);
        di.setProduct(product);

        List<DeliveryItem> items = List.of(di);
        when(deliveryItemRepository.findDeliveryItemsBySupplierIdAndProductId(
                anyLong(), anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(items);

        List<ReportResponseByDeliveryItemDto> report = reportService.getReport(1L, "2025-03-01", "2025-03-31", 1L);
        assertNotNull(report);
        assertEquals(1, report.size());

        ReportResponseByDeliveryItemDto dto = report.getFirst();
        assertEquals("Test Product", dto.getProductName());
        BigDecimal expected = BigDecimal.TEN.multiply(BigDecimal.valueOf(5));
        assertEquals(0, expected.compareTo(dto.getTotalCost()));
    }


    @Test
    void testGetReport_BySupplierIdOnly() {
        Delivery delivery = new Delivery();
        delivery.setDate(LocalDate.of(2025, 3, 15));
        delivery.setSupplier(new Supplier());
        delivery.getSupplier().setName("Supplier A");
        DeliveryItem di = new DeliveryItem();
        di.setId(1L);
        di.setPrice(BigDecimal.TEN);
        di.setQuantity(5);
        di.setDelivery(delivery);
        Product product = new Product();
        product.setId(2L);
        product.setName("Product B");
        product.setUnitOfMeasurement(UnitOfMeasurement.LITER);
        di.setProduct(product);

        List<DeliveryItem> items = List.of(di);
        when(deliveryItemRepository.findDeliveryItemsBySupplierId(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(items);

        List<ReportResponseByDeliveryItemDto> report = reportService.getReport(1L, "2025-03-01", "2025-03-31", null);
        assertNotNull(report);
        assertEquals(1, report.size());

        ReportResponseByDeliveryItemDto dto = report.getFirst();
        assertEquals("Supplier A", dto.getSupplierName());
        assertEquals("Product B", dto.getProductName());
        assertEquals(UnitOfMeasurement.LITER, dto.getUnitOfMeasurement());
    }
}