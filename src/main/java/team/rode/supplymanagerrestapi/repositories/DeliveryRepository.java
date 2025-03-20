package team.rode.supplymanagerrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.rode.supplymanagerrestapi.models.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
