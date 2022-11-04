package onde.there_batch.batch;

import java.util.List;
import lombok.RequiredArgsConstructor;
import onde.there_batch.batch.reader.JourneyItemReader;
import onde.there_batch.batch.reader.PlaceItemReader;
import onde.there_batch.batch.reader.RedisItemReader;
import onde.there_batch.batch.writer.JourneyItemWriter;
import onde.there_batch.batch.writer.JourneyThemeAndBookmarkWriter;
import onde.there_batch.batch.writer.PlaceItemWriter;
import onde.there_batch.redies.RedisService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Batch {

	private final String JOURNEY_ID = "journeyId";
	private final String PLACE_ID = "placeId";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final RedisService<String> redisService;
	private final JourneyItemWriter journeyItemWriter;
	private final PlaceItemWriter placeItemWriter;
	private final JourneyThemeAndBookmarkWriter journeyThemeAndBookmarkWriter;

	@Bean
	public Job journeyDeleteJob() {
		return this.jobBuilderFactory.get("JourneyDeleteJob")
			.incrementer(new RunIdIncrementer())
			.start(journeyThemeAndBookmarkDeleteStep())
			.next(placeDeleteStep())
			.next(journeyDeleteStep())
			.build();
	}

	@Bean
	public Job placeDeleteJob() {
		return this.jobBuilderFactory.get("PlaceDeleteJob")
			.incrementer(new RunIdIncrementer())
			.start(placeJobDeleteStep())
			.build();
	}

	@Bean
	public Step journeyThemeAndBookmarkDeleteStep() {
		return this.stepBuilderFactory.get("journeyThemeAndRegionDeleteStep")
			.<Long, Long>chunk(100)
			.reader(journeyItemReader(JOURNEY_ID))
			.writer(journeyThemeAndBookmarkWriter)
			.listener(promotionListener())
			.build();
	}

	@Bean
	public Step placeDeleteStep() {
		return this.stepBuilderFactory.get("placeDelete")
			.<Long, Long>chunk(100)
			.reader(placeItemReader(""))
			.writer(placeItemWriter)
			.listener(promotionListener())
			.build();
	}

	@Bean
	public Step journeyDeleteStep() {
		return this.stepBuilderFactory.get("journeyDelete")
			.<Long, Long>chunk(100)
			.reader(deleteJourneyItemReader())
			.writer(journeyItemWriter)
			.listener(promotionListener())
			.build();
	}

	@Bean
	public Step placeJobDeleteStep() {
		return this.stepBuilderFactory.get("placeDelete")
			.<Long, Long>chunk(100)
			.reader(placeItemReader("placeId"))
			.writer(placeItemWriter)
			.listener(promotionListener())
			.build();
	}

	@Bean
	public ExecutionContextPromotionListener promotionListener() {
		ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
		listener.setKeys(new String[]{"placeId", "journeyId"});
		return listener;
	}

	private ItemReader<Long> journeyItemReader(String key) {
		return new RedisItemReader(getItems(key));
	}

	private ItemReader<Long> deleteJourneyItemReader() {
		return new JourneyItemReader();
	}

	private ItemReader<Long> placeItemReader(String key) {
		return new PlaceItemReader(getItems(key));
	}

	private List<String> getItems(String redisKey) {
		return redisService.getListOps(redisKey);
	}
}
