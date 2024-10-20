package paperless.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paperless.models.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {
}
