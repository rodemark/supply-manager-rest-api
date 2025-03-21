package team.rode.supplymanagerrestapi.DTO.response;

import lombok.Data;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReportResponseByDeliveryItemDto {
    private String supplierName;
    private String productName;
    private UnitOfMeasurement unitOfMeasurement;
    private BigDecimal priceByUnit;
    private float quantity;
    private BigDecimal totalCost;
    private LocalDate deliveryDate;
}
