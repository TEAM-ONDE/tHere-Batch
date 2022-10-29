package onde.there_batch.repository;

import onde.there_batch.domain.PlaceHeartScheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceHeartSchedulingRepository extends JpaRepository<PlaceHeartScheduling, Long> {
	void deleteAllByPlaceId(Long placeId);
}
