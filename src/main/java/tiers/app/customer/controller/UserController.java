package tiers.app.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiers.app.customer.config.security.utils.JwtUtil;
import tiers.app.customer.model.Role;
import tiers.app.customer.model.User;
import tiers.app.customer.model.dto.UserRole;
import tiers.app.customer.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }


    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.ok().body(userService.saveUser(user));
    }
    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/assignrole")
    public ResponseEntity<?> assignRoleToUser(@RequestBody UserRole userRole){
        userService.assignRoleToUser(userRole.getUserName(), userRole.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
       jwtUtil.refreshToken(request,response);
    }

}
