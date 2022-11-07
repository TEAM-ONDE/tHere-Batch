package onde.there_batch.exception.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceErrorResponse {

	private PlaceErrorCode errorCode;
	private String errorMessage;
}
