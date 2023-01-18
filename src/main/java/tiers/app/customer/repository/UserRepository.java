package tiers.app.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.customer.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

}
