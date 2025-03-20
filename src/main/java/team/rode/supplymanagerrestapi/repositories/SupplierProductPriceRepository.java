package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;

import java.time.LocalDate;
import java.util.List;

public interface SupplierProductPriceRepository {

    // найти все цены, которые пересекаются с заданным периодом
    @Query("SELECT p FROM SupplierProductPrice p " +
            "WHERE p.supplier.id = :supplierId " +
            "AND p.product.id = :productId " +
            "AND p.startDate <= :endDate " +
            "AND p.endDate >= :startDate")
    List<SupplierProductPrice> findOverlappingPrices(
            @Param("supplierId") Long supplierId,
            @Param("productId") Long productId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
