package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierProductPriceRepository extends JpaRepository<SupplierProductPrice, Long> {

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

    // Найти актуальную цену на определённую дату (date)
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
}