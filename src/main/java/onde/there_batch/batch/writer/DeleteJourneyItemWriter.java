package onde.there_batch.batch.writer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.batch.SuperStepExecution;
import onde.there_batch.repository.JourneyRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class DeleteJourneyItemWriter extends SuperStepExecution<List<Long>> implements
	ItemWriter<Long> {

	private final JourneyRepository journeyRepository;
	@Override
	public void write(List<? extends Long> journeyIds) throws Exception {
		if (journeyIds.isEmpty()) {
			log.info("삭제할 여정이 없습니다.");
			return;
		}
		for (Long journeyId : journeyIds) {
			journeyRepository.deleteById(journeyId);
		}
	}

	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
		super.setStepExecution(stepExecution);
	}
}
