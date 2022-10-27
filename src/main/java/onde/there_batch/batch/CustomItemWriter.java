package onde.there_batch.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;

public class CustomItemWriter {

	@Bean
	public ItemWriter<Long> customJourneyItemWriter() {
		return items -> {
			for (Long journeyId : items) {
				System.out.println("journeyId = " + journeyId);
			}
		};
	}
	@Bean
	public ItemWriter<Long> customPlaceItemWriter() {
		return items -> {
			for (Long placeId : items) {
				System.out.println("placeId = " + placeId);
			}
		};
	}
}
