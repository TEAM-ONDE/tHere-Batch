package onde.there_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
public class ThereBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThereBatchApplication.class, args);
	}

}
