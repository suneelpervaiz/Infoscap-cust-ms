package tiers.app.customer.config.sms.cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Tokenstore that uses Redis to store, track, and expire tokens.
 */
@Service
public class CacheStore implements CacheService {

	private static final String INVALID_CODE = "verification Code is Invalid";
	private static final String MISMATCH_CODE ="Verification Code did not match. Please Enter correct code";
	private static final String EXPIRED_CODE = "Verification Code expired";
	private static final String VALID_CODE = "Verification Code Validated Successfully";
	private static final int MAX_WRONG_ATTEMPTS = 5;
	private static final String ATTEMPTS_LIMIT_EXCEEDED = "Maximum number os attempts limit exceeded, Please try latter";
	@Value("${redis.value.expiry}")
	private int maxLifetime;

	@Value("${redis.host}")
	private String redisHost;

	@Value("${redis.port}")
	private int redisPort;

	Jedis jedis= new Jedis();
	JedisPool jedisPool = new JedisPool(redisHost, redisPort);
	@Override
	public void putToken(String key, String value) {
		// Setup and execute transaction
		try {
			jedis.setex(key, maxLifetime, value);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getToken(String key) {
		String token = jedis.get(key);
		if (token != null)
			return token;
		else return EXPIRED_CODE;
	}

	@Override
	public String verifyEmail(String key, String value) {
		int counter;
		String counterKey = key+"_em";
		String val = (jedis.get(counterKey));
		if (val == null)
			counter=0;
		else
			counter= Integer.parseInt(val);
		if (counter < MAX_WRONG_ATTEMPTS) {
			String tokenString = jedis.get(key);
			if (tokenString == null){
				setCounter(counterKey, String.valueOf(counter+1));
				return ("Email " + INVALID_CODE);
			}
			else if (!tokenString.equals(value)){
				setCounter(counterKey, String.valueOf(counter+1));
				return ("Email " + MISMATCH_CODE);
			} else {
				jedis.del(key);
				jedis.del(counterKey);
				return ("Email " + VALID_CODE);
			}
		}else return ATTEMPTS_LIMIT_EXCEEDED;
	}

	@Override
	public String verifySMS(String key, String value) {
		int counter;
		String counterKey = key+"_ph";
		String val = (jedis.get(counterKey));
		if (val == null)
			counter=0;
		else
			counter= Integer.parseInt(val);
		if (counter < MAX_WRONG_ATTEMPTS) {
			String tokenString = jedis.get(key);
			if (tokenString == null){
				setCounter(counterKey, String.valueOf(counter+1));
				return ("SMS " + INVALID_CODE);
			}
			else if (!tokenString.equals(value)){
				setCounter(counterKey, String.valueOf(counter+1));
				return ("SMS " + MISMATCH_CODE);
			} else {
				jedis.del(key);
				jedis.del(counterKey);
				return ("SMS " + VALID_CODE);
			}
		}else return ATTEMPTS_LIMIT_EXCEEDED;
	}

	@Override
	public void setCounter(String key, String invalidAttempts) {
		jedis.set(key, invalidAttempts);
	}

	@Override
	public int getCounter(String key) {
		int counter = Integer.parseInt(jedis.get(key));
		return counter;
	}


}