package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
