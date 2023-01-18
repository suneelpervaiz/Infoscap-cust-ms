package tiers.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.user.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
