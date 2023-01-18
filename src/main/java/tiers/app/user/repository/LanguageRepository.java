package tiers.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiers.app.user.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
