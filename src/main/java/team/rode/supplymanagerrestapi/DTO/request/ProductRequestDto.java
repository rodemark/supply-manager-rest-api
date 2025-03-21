package team.rode.supplymanagerrestapi.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Unit of measurement is required")
    private UnitOfMeasurement unitOfMeasurement;
}
