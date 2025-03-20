package team.rode.supplymanagerrestapi.DTO.request;

import lombok.Data;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private UnitOfMeasurement unitOfMeasurement;
}
