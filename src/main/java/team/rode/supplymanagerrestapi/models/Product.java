package team.rode.supplymanagerrestapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measurement", nullable = false)
    private UnitOfMeasurement unitOfMeasurement;
}
