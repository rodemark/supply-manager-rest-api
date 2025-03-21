package team.rode.supplymanagerrestapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.supplymanagerrestapi.DTO.response.ReportResponseByDeliveryItemDto;
import team.rode.supplymanagerrestapi.models.Delivery;
import team.rode.supplymanagerrestapi.models.DeliveryItem;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;
import team.rode.supplymanagerrestapi.repositories.DeliveryItemRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    private final DeliveryItemRepository deliveryItemRepository;

    @Autowired
    public ReportService(DeliveryItemRepository deliveryItemRepository) {
        this.deliveryItemRepository = deliveryItemRepository;
    }

    public List<ReportResponseByDeliveryItemDto> getReport(Long supplierId, String startDateStr,
                                                           String endDateStr, Long productId) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        List<DeliveryItem> deliveryItems;

        if (productId == null) {
            deliveryItems = deliveryItemRepository.findDeliveryItemsBySupplierId(supplierId, startDate, endDate);
        } else {
            deliveryItems = deliveryItemRepository.findDeliveryItemsBySupplierIdAndProductId(productId, supplierId, startDate, endDate);
        }

        return getReportByDeliveryItemsList(deliveryItems);
    }

    private List<ReportResponseByDeliveryItemDto> getReportByDeliveryItemsList(List<DeliveryItem> deliveryItems) {
        List<ReportResponseByDeliveryItemDto> result = new ArrayList<>();
        for (DeliveryItem deliveryItem : deliveryItems) {
            Delivery delivery = deliveryItem.getDelivery();
            String supplierName = delivery.getSupplier().getName();
            LocalDate deliveryDate = delivery.getDate();
            String productName = deliveryItem.getProduct().getName();
            UnitOfMeasurement unitOfMeasurement = deliveryItem.getProduct().getUnitOfMeasurement();
            BigDecimal priceByUnit = deliveryItem.getPrice();
            float quantity = deliveryItem.getQuantity();
            BigDecimal totalCost = priceByUnit.multiply(BigDecimal.valueOf(quantity));

            ReportResponseByDeliveryItemDto dto = new ReportResponseByDeliveryItemDto();
            dto.setSupplierName(supplierName);
            dto.setProductName(productName);
            dto.setUnitOfMeasurement(unitOfMeasurement);
            dto.setPriceByUnit(priceByUnit);
            dto.setQuantity(quantity);
            dto.setTotalCost(totalCost);
            dto.setDeliveryDate(deliveryDate);
            result.add(dto);
        }
        return result;
    }
}