package url_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import url_shortener.entity.Hash;

@Repository
public interface HashRepository extends JpaRepository<Hash, Long> {
}