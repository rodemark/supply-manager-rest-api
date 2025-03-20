package team.rode.supplymanagerrestapi.DTO.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeliveryItemResponseDto {
    private Long id;
    private Long productId;
    private float quantity;
    private BigDecimal price;
}
