package tiers.app.customer.config.security.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpUtil {
    private static final Integer EXPIRE_MINS = 5;
    private LoadingCache otpCache;

    public OtpUtil(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public int generateOTP(String key){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }

    public int getOtp(String key){
        try {
            return (int) otpCache.get(key);
        } catch (ExecutionException e) {
            return 0;
        }
    }

    public void clearOtp(String key){
        otpCache.invalidate(key);
    }

}
