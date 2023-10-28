package url_shortener.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import url_shortener.entity.Hash;
import url_shortener.entity.Url;
import url_shortener.exception.url.UrlNotFoundException;
import url_shortener.repository.UrlCacheRepository;
import url_shortener.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlCacheRepository urlCacheRepository;

    @Mock
    private HashCache hashCache;

    @Mock
    private UrlRepository urlRepository;

    private final String originalURL = "https://example.com";
    private final String hash = "abc123";
    private final String serverAddress = "https://sh.c/";
    private Url testUrl;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(urlService, "serverAddress", serverAddress);

        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 1, 0, 0);

        testUrl = Url.builder()
                .url(originalURL)
                .hash(hash)
                .createdAt(createdAt)
                .build();
    }


    @Test
    public void testShortenUrl() {
        when(hashCache.getHash()).thenReturn(new Hash(hash));
        when(urlRepository.save(any(Url.class))).thenReturn(new Url());
        doNothing().when(urlCacheRepository).saveToCache(anyString(), anyString());

        String shortenedURL = urlService.shortenUrl(originalURL);

        assertNotNull(shortenedURL);
        assertTrue(shortenedURL.contains(hash));
    }

    @Test
    public void testExtractHashFromURL() {
        String shortURL = serverAddress + hash;

        Pattern pattern = Pattern.compile(Pattern.quote(serverAddress) + "([A-Za-z0-9]{6})");
        Matcher matcher = pattern.matcher(shortURL);

        assertTrue(matcher.find());
        String extractedHash = matcher.group(1);
        assertEquals("abc123", extractedHash);
    }

    @Test
    public void testGetOriginalURL() {
        when(urlRepository.findByHash(eq("abc123"))).thenReturn(testUrl);
        when(urlCacheRepository.getFromCache(eq("abc123"))).thenReturn(null);

        String resultURL = urlService.getOriginalURL(serverAddress + "abc123");

        assertEquals(originalURL, resultURL);
    }

    @Test
    public void testGetOriginalURLNotFound() {
        String hash = "nonExistentHash";

        assertThrows(UrlNotFoundException.class, () -> urlService.getOriginalURL(serverAddress + hash));
    }
}