package onde.there_batch.batch.scheduler;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.batch.JourneyDeleteJobConfig;
import onde.there_batch.batch.PlaceDeleteJobConfig;
import onde.there_batch.domain.Journey;
import onde.there_batch.domain.JourneyTheme;
import onde.there_batch.domain.Place;
import onde.there_batch.domain.PlaceHeart;
import onde.there_batch.redies.RedisService;
import onde.there_batch.repository.CommentRepository;
import onde.there_batch.repository.JourneyBookmarkRepository;
import onde.there_batch.repository.JourneyRepository;
import onde.there_batch.repository.JourneyThemeRepository;
import onde.there_batch.repository.MemberRepository;
import onde.there_batch.repository.PlaceHeartRepository;
import onde.there_batch.repository.PlaceImageRepository;
import onde.there_batch.repository.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JobSchedulerTest {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JourneyDeleteJobConfig journeyDeleteJobConfig;
	@Autowired
	private PlaceDeleteJobConfig placeDeleteJobConfig;

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private JourneyRepository journeyRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private JourneyBookmarkRepository journeyBookmarkRepository;
	@Autowired
	private JourneyThemeRepository journeyThemeRepository;
	@Autowired
	private PlaceHeartRepository placeHeartRepository;
	@Autowired
	private PlaceImageRepository placeImageRepository;

	@Autowired
	private RedisService<String> redisService;

	@BeforeEach
	public void setup() {
		log.info("setup==========================================");
		for (int i = 0; i < 100; i++) {
			Journey journey = journeyRepository.save(new Journey());
			redisService.setListOps("journeyId", String.valueOf(journey.getId()));
			JourneyTheme journeyTheme = journeyThemeRepository.save(
				JourneyTheme.builder().journey(journey).build());
			Place place = placeRepository.save(Place.builder().journey(journey).build());
			Place place2 = placeRepository.save(Place.builder().journey(journey).build());
			PlaceHeart placeHeart = placeHeartRepository.save(
				PlaceHeart.builder().place(place).build());
			PlaceHeart placeHeart2 = placeHeartRepository.save(
				PlaceHeart.builder().place(place2).build());
		}
		for (int i = 0; i < 100; i++) {
			Place place = placeRepository.save(Place.builder().build());
			redisService.setListOps("placeId", String.valueOf(place.getId()));
			PlaceHeart placeHeart = placeHeartRepository.save(
				PlaceHeart.builder().place(place).build());
		}
	}

	@Test
	void runJob() throws InterruptedException {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);

		try {

			jobLauncher.run(journeyDeleteJobConfig.journeyDeleteJob(), jobParameters);
			jobLauncher.run(placeDeleteJobConfig.placeDeleteJob(), jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
			| JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

			log.error(e.getMessage());
		}
	}
}