package org.pagebyfeel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(
            RedisTemplate<String, String> stringRedisTemplate,
            RedisTemplate<String, Object> redisTemplate
    ) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String BLACKLIST_PREFIX = "blacklist:";

    public void saveRefreshToken(UUID userId, String refreshToken, long expirationDays) {
        try {
            String key = REFRESH_TOKEN_PREFIX + userId.toString();
            stringRedisTemplate.opsForValue().set(
                    key,
                    refreshToken,
                    Duration.ofDays(expirationDays)
            );
            log.info("Refresh token saved for user: {}", userId);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while saving refresh token for user: {}. Token will not be cached.", userId, e);
        } catch (Exception e) {
            log.error("Unexpected error while saving refresh token for user: {}", userId, e);
        }
    }

    public String getRefreshToken(UUID userId) {
        try {
            String key = REFRESH_TOKEN_PREFIX + userId.toString();
            String token = stringRedisTemplate.opsForValue().get(key);
            log.debug("Retrieved refresh token for user: {}", userId);
            return token;
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while retrieving refresh token for user: {}. Returning null.", userId, e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while retrieving refresh token for user: {}", userId, e);
            return null;
        }
    }

    public void deleteRefreshToken(UUID userId) {
        try {
            String key = REFRESH_TOKEN_PREFIX + userId.toString();
            Boolean deleted = stringRedisTemplate.delete(key);
            log.info("Refresh token deleted for user: {}, success: {}", userId, deleted);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while deleting refresh token for user: {}. Token may remain in cache.", userId, e);
        } catch (Exception e) {
            log.error("Unexpected error while deleting refresh token for user: {}", userId, e);
        }
    }

    public boolean hasRefreshToken(UUID userId) {
        try {
            String key = REFRESH_TOKEN_PREFIX + userId.toString();
            Boolean exists = stringRedisTemplate.hasKey(key);
            return Boolean.TRUE.equals(exists);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while checking refresh token existence for user: {}. Returning false.", userId, e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected error while checking refresh token existence for user: {}", userId, e);
            return false;
        }
    }

    public void addToBlacklist(String accessToken, long expirationMinutes) {
        try {
            String key = BLACKLIST_PREFIX + accessToken;
            stringRedisTemplate.opsForValue().set(
                    key,
                    "blacklisted",
                    expirationMinutes,
                    TimeUnit.MINUTES
            );
            log.info("Access token added to blacklist");
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while adding token to blacklist. Token will not be blacklisted!", e);
        } catch (Exception e) {
            log.error("Unexpected error while adding token to blacklist", e);
        }
    }

    public boolean isBlacklisted(String accessToken) {
        try {
            String key = BLACKLIST_PREFIX + accessToken;
            Boolean blacklisted = stringRedisTemplate.hasKey(key);
            return Boolean.TRUE.equals(blacklisted);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while checking blacklist. Allowing request for availability (Fail-Open).", e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected error while checking blacklist. Allowing request for availability.", e);
            return false;
        }
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while setting key: {}", key, e);
        } catch (Exception e) {
            log.error("Unexpected error while setting key: {}", key, e);
        }
    }

    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while getting key: {}", key, e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while getting key: {}", key, e);
            return null;
        }
    }

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while deleting key: {}", key, e);
        } catch (Exception e) {
            log.error("Unexpected error while deleting key: {}", key, e);
        }
    }

    public boolean hasKey(String key) {
        try {
            Boolean exists = redisTemplate.hasKey(key);
            return Boolean.TRUE.equals(exists);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while checking key existence: {}", key, e);
            return false;
        } catch (Exception e) {
            log.error("Unexpected error while checking key existence: {}", key, e);
            return false;
        }
    }

    public Long getExpire(String key) {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while getting expiration for key: {}", key, e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while getting expiration for key: {}", key, e);
            return null;
        }
    }
}
