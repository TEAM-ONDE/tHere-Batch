package onde.there_batch.batch;

import java.util.List;
import lombok.RequiredArgsConstructor;
import onde.there_batch.domain.Place;
import onde.there_batch.repository.JourneyRepository;
import onde.there_batch.repository.PlaceRepository;
import onde.there_batch.redies.RedisService;
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

	private final RedisService<Long> redisService;

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
			.<Long, Long>chunk(100)
			.reader(new CustomItemReaderForRedis<Long>(getItems(JOURNEY_ID)))
			.processor(checkHavePlace())
			.writer()
			.build();
	}

	@Bean
	public Step placeDeleteStep() {
		return this.stepBuilderFactory.get("placeDelete")
			.chunk(100)
			.reader(new CustomItemReaderForRedis<>(getItems(PLACE_ID))
				.writer()
				.build();
	}


	private List<Long> getItems(String redisKey) {
		return (List<Long>) redisService.getSetOps(redisKey);
	}

	private ItemProcessor<? super Long, ? extends Long> checkHavePlace() {
		return journeyId -> {
			List<Place> places = placeRepository.findAllByJourneyId(journeyId);
			if (!places.isEmpty()) {
				for (Place place : places) {
					redisService.setSetOps(PLACE_ID, (place.getId()));
				}
			}

			return (Long) journeyId;
		};
	}
}
