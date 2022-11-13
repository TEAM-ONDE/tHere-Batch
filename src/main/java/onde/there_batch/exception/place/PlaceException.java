package onde.there_batch.exception.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class PlaceException extends RuntimeException {

	private PlaceErrorCode errorCode;
	private String errorMessage;

	public PlaceException(PlaceErrorCode errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}

}
