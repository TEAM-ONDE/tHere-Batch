package onde.there_batch.batch;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import onde.there_batch.redies.RedisService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

@RequiredArgsConstructor
public class CustomItemReaderForRedis<T> implements ItemReader<T> {

	private List<T> list;

	public CustomItemReaderForRedis(List<T> list) {
		this.list = new ArrayList<>(list);
	}

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (!list.isEmpty()) {
			return list.remove(0); // 1개씩 읽을때마다 데이터를 삭제
		}
		return null; // null 을 반환하면 더이상 읽을 아이템이 없다는 것으로 설정됨
	}
}
