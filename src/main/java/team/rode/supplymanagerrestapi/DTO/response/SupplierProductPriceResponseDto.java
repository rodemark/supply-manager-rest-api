package team.rode.supplymanagerrestapi.DTO.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SupplierProductPriceResponseDto {
    private Long id;
    private Long supplierId;
    private String supplierName; // если нужно
    private Long productId;
    private String productName; // если нужно
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
}
