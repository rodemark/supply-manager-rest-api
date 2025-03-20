package team.rode.supplymanagerrestapi.DTO.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class DeliveryResponseDto {
    private Long id;
    private Long supplierId;
    private String supplierName;
    private LocalDate date;
    private List<DeliveryItemResponseDto> deliveryItemList;
    private BigDecimal totalCost;
}
