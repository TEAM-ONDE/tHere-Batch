package onde.there_batch.batch.reader;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class RedisItemReader implements ItemReader<Long> {
	List<Long> list;

	public RedisItemReader(List<String> list) {
		this.list = list.stream().map(Long::parseLong).collect(Collectors.toList());
	}

	@Override
	public Long read()
		throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!list.isEmpty()) {
			return list.remove(0);
		}
		return null;
	}
}
