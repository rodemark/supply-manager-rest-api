package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.Product;
import team.rode.supplymanagerrestapi.models.additionally.UnitOfMeasurement;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Product p " +
            "WHERE p.name = :name " +
            "AND p.description = :description " +
            "AND p.unitOfMeasurement = :unitOfMeasurement")
    boolean existsSimilarProduct(
            @Param("name") String name,
            @Param("description") String description,
            @Param("unitOfMeasurement") UnitOfMeasurement unitOfMeasurement
    );
}
