package tiers.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

}
