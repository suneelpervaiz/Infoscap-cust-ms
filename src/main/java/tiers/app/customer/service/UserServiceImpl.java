package tiers.app.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tiers.app.customer.model.Role;
import tiers.app.customer.model.User;
import tiers.app.customer.repository.RoleRepository;
import tiers.app.customer.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Predicate;

@Service @RequiredArgsConstructor @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void assignRoleToUser(String userName, String roleName) {
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        Predicate<User> userPredicate = x->x == null;
        if (userPredicate.test(user))
            throw new UsernameNotFoundException("User not found in database");
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
