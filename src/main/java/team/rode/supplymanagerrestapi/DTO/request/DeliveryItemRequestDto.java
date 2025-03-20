package team.rode.supplymanagerrestapi.DTO.request;

import lombok.Data;

@Data
public class DeliveryItemRequestDto {
    private Long deliveryId;
    private Long productId;
    private float quantity;
}
