package team.rode.supplymanagerrestapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "delivery")
@Getter
@Setter
public class Delivery extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryItem> deliveryItemList;

    @Transient
    public BigDecimal getTotalCost() {
        return deliveryItemList.stream()
                .map(deliveryItem -> deliveryItem.getPrice().multiply(BigDecimal.valueOf(deliveryItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}