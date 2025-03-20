package team.rode.supplymanagerrestapi.DTO.response;

import lombok.Data;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private UnitOfMeasurement unitOfMeasurement;
}
