package team.rode.supplymanagerrestapi.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DeliveryRequestDto {

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotEmpty(message = "Delivery item list cannot be empty")
    private List<DeliveryItemRequestDto> deliveryItemList;
}
