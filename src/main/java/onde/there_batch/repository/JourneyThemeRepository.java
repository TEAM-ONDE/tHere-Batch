package onde.there_batch.repository;

import onde.there_batch.domain.JourneyTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyThemeRepository extends JpaRepository<JourneyTheme, Long> {
	void deleteAllByJourneyId(Long journeyId);
}
