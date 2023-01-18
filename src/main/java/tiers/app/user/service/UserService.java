package tiers.app.user.service;

import tiers.app.user.model.Role;
import tiers.app.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void assignRoleToUser(String userName, String roleName);
    Optional<User> getUserById(Long id);
    User getUser(String userName);
    List<User> getUsers();
}
