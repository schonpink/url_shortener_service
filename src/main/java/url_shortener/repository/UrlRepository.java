package url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import url_shortener.entity.Url;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByHash(String hash);
}