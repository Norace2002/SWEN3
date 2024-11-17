package paperless.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paperless.models.Metadata;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, String> {
}
