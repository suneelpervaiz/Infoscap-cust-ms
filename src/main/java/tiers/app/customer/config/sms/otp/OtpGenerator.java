package tiers.app.customer.config.sms.otp;

import org.springframework.stereotype.Service;

/**
 * Implementations of this interface generate random token strings.
 */
public interface OtpGenerator {
	
	/**
	 * Generates a random token.
	 */
	public String generateToken();
}
