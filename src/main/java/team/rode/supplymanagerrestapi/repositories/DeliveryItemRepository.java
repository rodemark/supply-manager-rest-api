package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.DeliveryItem;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Long> {
    @Query("SELECT di FROM DeliveryItem di JOIN di.delivery d " +
            "WHERE d.supplier.id = :supplierId " +
            "AND di.product.id = :productId " +
            "AND d.date BETWEEN :startDate AND :endDate")
    List<DeliveryItem> findDeliveryItemsBySupplierIdAndProductId(
            @Param("productId") Long productId,
            @Param("supplierId") Long supplierId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT di FROM DeliveryItem di JOIN di.delivery d " +
            "WHERE d.supplier.id = :supplierId " +
            "AND d.date BETWEEN :startDate AND :endDate")
    List<DeliveryItem> findDeliveryItemsBySupplierId(
            @Param("supplierId") Long supplierId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
