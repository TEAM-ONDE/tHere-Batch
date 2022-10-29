package onde.there_batch.batch.writer;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.batch.SuperStepExecution;
import onde.there_batch.domain.Place;
import onde.there_batch.redies.RedisService;
import onde.there_batch.repository.JourneyBookmarkRepository;
import onde.there_batch.repository.JourneyThemeRepository;
import onde.there_batch.repository.PlaceRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class JourneyItemWriter extends SuperStepExecution<List<Long>> implements ItemWriter<Long> {

	private final PlaceRepository placeRepository;
	private final JourneyBookmarkRepository journeyBookmarkRepository;
	private final JourneyThemeRepository journeyThemeRepository;

	@Override
	public void write(List<? extends Long> journeyIds) throws Exception {
		if(journeyIds.isEmpty()){
			log.info("삭제할 여정이 없습니다.");
			return;
		}
		List<Long> placeIds = new ArrayList<>();
		for (Long journeyId : journeyIds) {
			List<Place> places = placeRepository.findAllByJourneyId(journeyId);
			if(places.isEmpty()){
				log.info("JourneyId : " + journeyId + "에 저장된 장소가 없습니다.");
				continue;
			}
			for (Place place : places) {
				placeIds.add(place.getId());
			}
			journeyBookmarkRepository.deleteAllByJourneyId(journeyId);
			journeyThemeRepository.deleteAllByJourneyId(journeyId);
			log.info("JourneyId : " + journeyId + "의 테마, 찜(북마크) 삭제 완료");
		}
		super.putData("key", placeIds);

	}
	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
		super.setStepExecution(stepExecution);
	}
}
