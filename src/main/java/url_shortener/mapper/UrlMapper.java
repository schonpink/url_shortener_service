package url_shortener.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import url_shortener.dto.UrlDTO;
import url_shortener.entity.Url;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UrlMapper {
    UrlDTO toDto(Url url);
    Url toUrl(UrlDTO dto);
}