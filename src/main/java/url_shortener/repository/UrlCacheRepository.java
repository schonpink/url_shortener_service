package url_shortener.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
@Component
@Repository
@RequiredArgsConstructor
public class UrlCacheRepository {
    private final RedisTemplate<String, String> redisUrlTemplate;

    @Cacheable(value = "popularUrls", key = "#hash", unless = "#result == null", condition = "#result != null")
    public void saveToCache(String hash, String originalURL) {
        redisUrlTemplate.opsForValue().set(hash, originalURL, 2, TimeUnit.DAYS);
    }

    @Cacheable(value = "popularUrls", key = "#hash", unless = "#result == null")
    public String getFromCache(String hash) {
        return redisUrlTemplate.opsForValue().get(hash);
    }
}