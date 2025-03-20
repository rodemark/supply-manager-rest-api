package team.rode.supplymanagerrestapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "delivery_item")
@Getter
@Setter
public class DeliveryItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private float quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
