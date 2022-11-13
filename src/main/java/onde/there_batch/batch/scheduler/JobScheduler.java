package onde.there_batch.batch.scheduler;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.batch.JourneyDeleteJobConfig;
import onde.there_batch.batch.PlaceDeleteJobConfig;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobScheduler {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JourneyDeleteJobConfig journeyDeleteJobConfig;

	@Autowired
	private PlaceDeleteJobConfig placeDeleteJobConfig;

	@Scheduled(cron = "0 * * * * *")
	public void runJob() {

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