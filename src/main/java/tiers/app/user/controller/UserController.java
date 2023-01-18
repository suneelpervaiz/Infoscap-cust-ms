package tiers.app.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiers.app.user.config.security.filter.JwtUtil;
import tiers.app.user.model.Role;
import tiers.app.user.model.User;
import tiers.app.user.model.dto.UserRole;
import tiers.app.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @Value("${tiers.app.jwt.secret}")
    private String jwtSecret;

/*    @Value("${tiers.app.jwt.expiry}")
    private int jwtExpirationInMs;*/

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
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String userName = decodedJWT.getSubject();
                User user = userService.getUser(userName);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }


    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/result")
    public String getResult(){
        jwtUtil.ApplyEncryptionAlgorithm();
        return null;
    }

}
