package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN true ELSE false END " +
            "FROM Supplier sp " +
            "WHERE sp.name = :name " +
            "AND sp.contact = :contact")
    boolean existsSimilarSupplier(
            @Param("name") String name,
            @Param("contact") String contact
    );
}