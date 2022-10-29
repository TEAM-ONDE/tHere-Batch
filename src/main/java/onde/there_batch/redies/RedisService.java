package onde.there_batch.redies;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService<T> {

	private final RedisTemplate<String, T> redisTemplate;

	public final void setSetOps(String key, T... values) {
		redisTemplate.opsForSet().add(key, values);

	}

	public List<T> getListOps(String key) {
		return new ArrayList<T>(
			Objects.requireNonNull(redisTemplate.opsForSet().members(key)));
	}

	public boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

}
