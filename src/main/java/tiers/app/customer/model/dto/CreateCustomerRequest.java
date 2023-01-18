package tiers.app.customer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiers.app.customer.model.Country;
import tiers.app.customer.model.Device;
import tiers.app.customer.model.Language;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Role name is required")
    private String roleName;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Pin code name is required")
    private String pinCode;
    private boolean isApproved;
    private String approvedBy;
    private Timestamp approvedAt;

    @NotNull(message = "Language cannot be null")
    private Language language;

    @NotNull(message = "Device cannot be null")
    private Device device;

    @NotNull(message = "Country cannot be null")
    private Country country;

}
