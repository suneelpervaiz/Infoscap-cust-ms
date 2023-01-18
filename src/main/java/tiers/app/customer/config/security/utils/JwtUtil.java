package tiers.app.customer.config.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import tiers.app.customer.config.security.helper.EmailService;
import tiers.app.customer.config.security.helper.EmailTemplate;
import tiers.app.customer.model.Role;
import tiers.app.customer.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtUtil {

    @Value("${tiers.app.jwt.secret}")
    private String jwtSecret ;
    @Value("${tiers.app.jwt.expiry}")
    private int jwtExpirationInMs;

    @Value("${tiers.app.api.url}")
    private String url;

    @Value("${tiers.app.jwt.refresh.token.expiry}")
    private int refreshTokenExpirationInMs;

    @Autowired
    private UserService userService;

    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    EmailService emailService;


    public String createAccessToken(HttpServletRequest request, Authentication authResult){
        User user = (User) authResult.getPrincipal();
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+jwtExpirationInMs))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(applyEncryptionAlgorithm());
        return access_token;
    }

    public String createRefreshToken(HttpServletRequest request, Authentication authResult){
        User user = (User) authResult.getPrincipal();
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+refreshTokenExpirationInMs))
                .withIssuer(request.getRequestURL().toString())
                .sign(applyEncryptionAlgorithm());
        return refresh_token;
    }

    public Algorithm applyEncryptionAlgorithm(){
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }

    public String getUrl() {
        return url;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                JWTVerifier verifier = JWT.require(applyEncryptionAlgorithm()).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String userName = decodedJWT.getSubject();
                tiers.app.customer.model.User user = userService.getUser(userName);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(applyEncryptionAlgorithm());
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    public void sendOtpInMail(Authentication auth) throws MessagingException {
        String username = auth.getName();
        int otp = otpUtil.generateOTP(username);
        EmailTemplate template = new EmailTemplate("SendOtp.html");
        Map<String,String> replacements = new HashMap<String,String>();
        replacements.put("user", username);
        replacements.put("otpnum", String.valueOf(otp));
        String message = template.getTemplate(replacements);
        emailService.sendOtpMessage("suneelpervaiz@gmail.com", "OTP -SpringBoot", message);
    }

    public String validateOtp(Authentication auth, int otp){
        final String SUCCESS = "Entered Otp is valid";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        String username = auth.getName();
        //Validate the Otp
        if(otp >= 0){
            int serverOtp = otpUtil.getOtp(username);
            if(serverOtp > 0){
                if(otp == serverOtp){
                    otpUtil.clearOtp(username);
                    return ("Entered Otp is valid");
                }
                else {
                    return FAIL;
                }
            }else {
                return FAIL;
            }
        }else {
            return FAIL;
        }
    }
}