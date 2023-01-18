package tiers.app.customer.service;

import tiers.app.customer.model.Role;
import tiers.app.customer.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void assignRoleToUser(String userName, String roleName);
    Optional<User> getUserById(Long id);
    User getUser(String userName);
    List<User> getUsers();
    Role findRoleByName(String roleName);
}
