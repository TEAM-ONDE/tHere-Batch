package onde.there_batch.batch;

import lombok.RequiredArgsConstructor;
import onde.there_batch.batch.reader.PlaceItemReader;
import onde.there_batch.batch.writer.PlaceItemWriter;
import onde.there_batch.redies.RedisService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PlaceDeleteJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final RedisService<String> redisService;
	private final PlaceItemWriter placeItemWriter;
	private final String PLACE_ID = "placeId";

	private final long REDIS_GET_PLACE_SIZE = 100L;

	@Bean
	public Job placeDeleteJob() {
		return this.jobBuilderFactory.get("PlaceDeleteJob")
			.incrementer(new RunIdIncrementer())
			.start(placeJobDeleteStep())
			.build();
	}

	@Bean
	@JobScope
	public Step placeJobDeleteStep() {
		return this.stepBuilderFactory.get("placeDelete")
			.<Long, Long>chunk(100)
			.reader(placeItemReader(PLACE_ID))
			.writer(placeItemWriter)
			.build();
	}

	private ItemReader<Long> placeItemReader(String key) {
		return new PlaceItemReader(redisService.getListOps(key, REDIS_GET_PLACE_SIZE));
	}
}
