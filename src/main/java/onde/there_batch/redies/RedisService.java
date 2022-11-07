package onde.there_batch.redies;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService<T> {

	private final RedisTemplate<String, T> redisTemplate;

	public final void setListOps(String key, T value){
		redisTemplate.opsForList().rightPush(key, value);
	}
	public List<T> getListOps(String key, long count){
		List<T> result = redisTemplate.opsForList().range(key, 0, count);
		result.forEach(v -> redisTemplate.opsForHash().delete(key, v));
		return result;
	}


	public boolean delete(String key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}

	public boolean hasKey(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

}
