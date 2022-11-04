package onde.there_batch.batch.reader;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.batch.SuperStepExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class JourneyItemReader extends SuperStepExecution<Long> implements ItemReader<Long> {

	private List<Long> journeyIds;

	@Override
	public Long read()
		throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!journeyIds.isEmpty()) {
			return journeyIds.remove(0);
		}
		return null;
	}

	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		super.setStepExecution(stepExecution);
		this.journeyIds = (List<Long>) super.getData("journeyId");
	}
}
