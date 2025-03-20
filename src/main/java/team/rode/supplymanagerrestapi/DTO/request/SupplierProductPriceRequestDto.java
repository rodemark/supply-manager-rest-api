package team.rode.supplymanagerrestapi.DTO.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SupplierProductPriceRequestDto {
    private Long supplierId;
    private Long productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
}
