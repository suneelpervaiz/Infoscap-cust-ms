package tiers.app.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.customer.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
