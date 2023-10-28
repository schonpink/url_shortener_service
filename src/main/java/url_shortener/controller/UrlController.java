package url_shortener.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import url_shortener.exception.url.InvalidUrlException;
import url_shortener.service.UrlService;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("${url.prefix}/url")

public class UrlController {
    private final UrlService urlService;
    @Value("${url.shortener-service.address}")
    private String serverAddress;

    @PostMapping
    public ResponseEntity<String> shortenUrl(@RequestBody String url) {
        String shortUrl = urlService.shortenUrl(url);
        log.info("Received request to shorten URL: {}", url);

        if (!isValidUrl(url)) {
            throw new InvalidUrlException("Invalid URL");
        }

        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{hash}")
    public void redirectToOriginalURL(@PathVariable String hash, HttpServletResponse response) {
        String originalURL = urlService.getOriginalURL(serverAddress + hash);
        if (originalURL != null) {
            response.setHeader("Location", originalURL);
            response.setStatus(HttpServletResponse.SC_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean isValidUrl(String url) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https", "ftp"});

        return urlValidator.isValid(url);
    }
}