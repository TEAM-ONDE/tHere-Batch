package onde.there_batch.repository;

import java.util.List;
import onde.there_batch.domain.JourneyBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyBookmarkRepository extends JpaRepository<JourneyBookmark, Long> {
	void deleteAllByJourneyId(Long journeyId);
}
