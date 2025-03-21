package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SupplierProductPriceRepository extends JpaRepository<SupplierProductPrice, Long> {
    @Query("SELECT p FROM SupplierProductPrice p " +
            "WHERE p.supplier.id = :supplierId " +
            "AND p.product.id = :productId " +
            "AND p.startDate <= :date " +
            "AND p.endDate >= :date")
    Optional<SupplierProductPrice> findCurrentPrice(
            @Param("supplierId") Long supplierId,
            @Param("productId") Long productId,
            @Param("date") LocalDate date
    );

    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN true ELSE false END " +
            "FROM SupplierProductPrice sp " +
            "WHERE sp.supplier.id = :supplierId " +
            "AND sp.product.id = :productId " +
            "AND sp.startDate = :startDate " +
            "AND sp.endDate = :endDate " +
            "AND sp.price = :price")
    boolean existsSimilarSupplierProductPrice(
            @Param("supplierId") Long supplierId,
            @Param("productId") Long productId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("price") BigDecimal price
    );
}