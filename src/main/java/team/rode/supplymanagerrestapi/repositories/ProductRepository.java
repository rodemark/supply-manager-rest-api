package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
