package tiers.app.user.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {


    @Value("${tiers.app.jwt.secret}")
    private String jwtSecret ;

    @Value("${tiers.app.jwt.expiry}")
    private int jwtExpirationInMs;


    public String createAccessToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = ApplyEncryptionAlgorithm();



        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+jwtExpirationInMs))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return access_token;
    }

    public String createRefreshToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = ApplyEncryptionAlgorithm();
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+jwtExpirationInMs))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        return refresh_token;
    }

    public Algorithm ApplyEncryptionAlgorithm(){

        System.out.println("----------JWT EXPIRATION IS-------------"+jwtExpirationInMs);
        System.out.println("----------JWT Secret IS-------------"+jwtSecret);
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }





}
