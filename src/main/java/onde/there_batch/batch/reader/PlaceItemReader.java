package onde.there_batch.batch.reader;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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
public class PlaceItemReader extends SuperStepExecution<Long> implements ItemReader<Long> {

	private List<Long> placeIds;
	private List<Long> redisPlaceIds;


	public PlaceItemReader(List<String> redisPlaceIds) {
		this.redisPlaceIds = redisPlaceIds.stream().map(Long::parseLong).collect(Collectors.toList());
	}

	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		super.setStepExecution(stepExecution);
		this.placeIds = (List<Long>) super.getData("key");
	}

	@Override
	public Long read()
		throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(!placeIds.isEmpty()){
			return placeIds.remove(0);
		} else if(!redisPlaceIds.isEmpty()){
			return redisPlaceIds.remove(0);
		}
		return null;
	}
}
