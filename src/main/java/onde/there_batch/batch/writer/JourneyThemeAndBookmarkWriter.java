package onde.there_batch.batch.writer;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.batch.SuperStepExecution;
import onde.there_batch.domain.Place;
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
public class JourneyThemeAndBookmarkWriter extends SuperStepExecution<List<Long>> implements
	ItemWriter<Long> {

	private final PlaceRepository placeRepository;
	private final JourneyBookmarkRepository journeyBookmarkRepository;
	private final JourneyThemeRepository journeyThemeRepository;

	@Override
	public void write(List<? extends Long> journeyIds) throws Exception {
		if (journeyIds.isEmpty()) {
			log.info("삭제할 여정이 없습니다.");
			return;
		}
		log.info("JourneyThemeAndBookmarkWriter 시작 사이즈 : " + journeyIds.size());
		List<Long> journeyList = new ArrayList<>();
		List<Long> placeList = new ArrayList<>();
		for (Long journeyId : journeyIds) {
			journeyList.add(journeyId);
			List<Place> places = placeRepository.findAllByJourneyId(journeyId);
			if (places.isEmpty()) {
				log.info("JourneyId : " + journeyId + "에 저장된 장소가 없습니다.");
				continue;
			}
			for (Place place : places) {
				placeList.add(place.getId());
			}
			journeyBookmarkRepository.deleteAllByJourneyId(journeyId);
			journeyThemeRepository.deleteAllByJourneyId(journeyId);
		}
		log.info("JourneyThemeAndBookmarkWriter -> 테마, 북마크 삭제 완료");
		super.putData("journeyId", journeyList);
		super.putData("placeId", placeList);
	}

	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
		super.setStepExecution(stepExecution);
	}
}
