package team.rode.supplymanagerrestapi.DTO.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DeliveryRequestDto {
    private Long supplierId;
    private LocalDate date;
    private List<DeliveryItemRequestDto> deliveryItemList;
}
