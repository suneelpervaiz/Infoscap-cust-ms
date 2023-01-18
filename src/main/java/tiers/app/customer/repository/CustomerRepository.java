package tiers.app.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByPhoneNumber(String phoneNumber);
}
