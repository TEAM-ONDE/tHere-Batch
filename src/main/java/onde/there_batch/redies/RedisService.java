package onde.there_batch.redies;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService<T> {

	private final RedisTemplate<String, T> redisTemplate;

	public final void setSetOps(String key, T... values) {
		redisTemplate.opsForSet().add(key, values);
	}

	public Set<T> getSetOps(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	public boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

}
