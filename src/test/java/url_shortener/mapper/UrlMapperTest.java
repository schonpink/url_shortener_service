package url_shortener.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import url_shortener.dto.UrlDTO;
import url_shortener.entity.Url;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UrlMapperTest {
    @Spy
    private UrlMapperImpl mapper;

    private Url expectedUrl;
    private UrlDTO expectedDto;

    @BeforeEach
    void setUp() {
        LocalDateTime createdAt = LocalDateTime.of(2023, 1, 1, 0, 0);

        expectedUrl = Url.builder()
                .url("https://example.com")
                .createdAt(createdAt)
                .build();

        expectedDto = UrlDTO.builder()
                .url("https://example.com")
                .build();
    }

    @Test
    void testToDto() {
        UrlDTO actualUrlDTO = mapper.toDto(expectedUrl);
        assertEquals(expectedDto, actualUrlDTO);
    }

    @Test
    void testToUrl() {
        Url url = mapper.toUrl(expectedDto);
        assertEquals(expectedDto.getUrl(), url.getUrl());
    }
}