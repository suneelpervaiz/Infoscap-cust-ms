package tiers.app.customer.config.sms.cache;

/**
 * Implementations of this interface store tokens and invalidate them after
 * a configured period of time.
 */
public interface CacheService {
	/**
	 * Adds a username/token pair to the Tokenstore.
	 */
	public void putToken(String key, String value);
	public String getToken(String key);
	public String verifySMS(String key, String value);
	public String verifyEmail(String key, String value);
	public void setCounter(String key, String invalidAttempts);
	public int getCounter(String key);
}
