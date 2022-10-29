package onde.there_batch.repository;

import java.util.List;
import onde.there_batch.domain.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {

	List<PlaceImage> findAllByPlaceId(Long placeId);

	void deleteAllByPlaceId(Long placeId);
}
