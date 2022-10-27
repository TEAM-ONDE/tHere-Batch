package onde.there_batch.batch;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import onde.there_batch.domain.Place;
import onde.there_batch.redies.RedisService;
import onde.there_batch.repository.JourneyRepository;
import onde.there_batch.repository.PlaceRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class batch {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final RedisService<String> redisService;

	private final String JOURNEY_ID = "journeyId";

	private final String PLACE_ID = "placeId";

	private final JourneyRepository journeyRepository;
	private final PlaceRepository placeRepository;


	@Bean
	public Job delete_job() {
		return this.jobBuilderFactory.get("delete_job")
			.incrementer(new RunIdIncrementer())
			.start(journeyDeleteStep())
			.next(placeDeleteStep())
			.build();
	}

	@Bean
	public Step journeyDeleteStep() {
		return this.stepBuilderFactory.get("journeyDelete")
			.<String, Long>chunk(100)
			//.reader(new CustomItemReaderForRedis<String>(getItems(JOURNEY_ID)))
			.reader(new CustomItemReaderForRedis<String>(getItems(JOURNEY_ID)))
			.processor(checkHavePlace())
			.writer(new CustomItemWriter().customJourneyItemWriter())
			.build();
	}

	@Bean
	public Step placeDeleteStep() {
		return this.stepBuilderFactory.get("placeDelete")
			.<String, Long>chunk(100)
			.reader(new CustomItemReaderForRedis<String>(getItems(PLACE_ID)))
			.writer(new CustomItemWriter().customPlaceItemWriter())
			.build();
	}


	private List<String> getItems(String redisKey) {
		List<String> list = new ArrayList<String>(redisService.getSetOps(redisKey));
		return new ArrayList<String>(redisService.getSetOps(redisKey));
	}

	private ItemProcessor<? super String, ? extends Long> checkHavePlace() {
		return journeyId -> {
			List<Place> places = placeRepository.findAllByJourneyId(Long.valueOf(journeyId));
			if (!places.isEmpty()) {
				for (Place place : places) {
					redisService.setSetOps(PLACE_ID, String.valueOf(place.getId()));
				}
			}

			return Long.parseLong(journeyId);
		};
	}
}
