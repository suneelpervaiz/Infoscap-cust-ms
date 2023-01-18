package tiers.app.customer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailVerificationRequest {

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "verification code is required")
    String verificationCode;
}
