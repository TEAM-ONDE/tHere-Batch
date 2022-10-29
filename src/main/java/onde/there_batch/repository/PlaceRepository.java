package onde.there_batch.repository;

import java.util.List;
import onde.there_batch.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	List<Place> findAllByJourneyId(Long journeyId);
}
