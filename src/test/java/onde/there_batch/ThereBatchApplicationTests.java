package onde.there_batch;

import onde.there_batch.domain.Journey;
import onde.there_batch.domain.Place;
import onde.there_batch.redies.RedisService;
import onde.there_batch.repository.JourneyRepository;
import onde.there_batch.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThereBatchApplicationTests {
	@Autowired
	private JourneyRepository journeyRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private RedisService<String> redisService;
	@Test
	void aa() {
		for (int i = 0; i < 100; i++) {
			Journey journey = journeyRepository.save(new Journey());
			redisService.setSetOps("journeyId", String.valueOf(journey.getId()));
			Place place = placeRepository.save(Place.builder().journey(journey).build());
		}
	}
}
