package onde.there_batch.exception.journey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JourneyException extends RuntimeException {

	private JourneyErrorCode errorCode;
	private String errorMessage;

	public JourneyException(JourneyErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
