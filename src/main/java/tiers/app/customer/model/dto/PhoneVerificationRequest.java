package tiers.app.customer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneVerificationRequest {

    @NotBlank(message = "Phone number is required")
    String phoneNumber;

    @NotBlank(message = "verification code is required")
    String verificationCode;
}
