package onde.there_batch.redies;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService<T> {

	private final RedisTemplate<String, T> redisTemplate;

	public final void setListOps(String key, T value) {
		redisTemplate.opsForList().rightPush(key, value);
	}

	public List<T> getListOps(String key, long count) {
		Long size = redisTemplate.opsForList().size(key);
		List<T> result = new ArrayList<>();
		if (size == null) {
			return result;
		}
		if (size < count) {
			count = size;
		}
		for (int i = 0; i < count; i++) {
			result.add(redisTemplate.opsForList().leftPop(key));
		}
		return result;
	}


	public boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

}
