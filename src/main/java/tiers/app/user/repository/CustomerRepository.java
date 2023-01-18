package tiers.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.user.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
