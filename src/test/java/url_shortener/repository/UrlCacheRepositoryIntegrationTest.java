package url_shortener.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlCacheRepositoryIntegrationTest {

    @Autowired
    private UrlCacheRepository urlCacheRepository;

    @Test
    public void testCaching() {
        String hash = "abc123";
        String originalURL = "https://example.com";

        urlCacheRepository.saveToCache(hash, originalURL);
        assertEquals(originalURL, urlCacheRepository.getFromCache(hash));

        assertEquals(originalURL, urlCacheRepository.getFromCache(hash));
    }
}