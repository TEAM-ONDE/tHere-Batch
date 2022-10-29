package onde.there_batch.repository;

import onde.there_batch.domain.PlaceHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceHeartRepository extends JpaRepository<PlaceHeart, Long> {

	void deleteAllByPlaceId(Long placeId);
}
