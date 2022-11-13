package onde.there_batch.exception;

import lombok.extern.slf4j.Slf4j;
import onde.there_batch.exception.journey.JourneyErrorResponse;
import onde.there_batch.exception.journey.JourneyException;
import onde.there_batch.exception.member.MemberErrorResponse;
import onde.there_batch.exception.member.MemberException;
import onde.there_batch.exception.place.PlaceErrorResponse;
import onde.there_batch.exception.place.PlaceException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(
		MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST,
			e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(
		HttpMessageNotReadableException e) {
		ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST,
			"Request Body가 비어 있습니다");
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<?> handleMemberException(MemberException e) {
		MemberErrorResponse errorResponse = new MemberErrorResponse(e.getMemberErrorCode(),
			e.getErrorMessage());
		log.error("errorCode => {}", errorResponse.getErrorCode());
		log.error("errorMessage => {}", errorResponse.getErrorMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}


	@ExceptionHandler(PlaceException.class)
	public ResponseEntity<?> handlerPlaceException(PlaceException e) {

		return ResponseEntity.badRequest()
			.body(PlaceErrorResponse.builder()
				.errorCode(e.getErrorCode())
				.errorMessage(e.getErrorMessage())
				.build());
	}

	@ExceptionHandler(JourneyException.class)
	public ResponseEntity<?> handleJourneyException(JourneyException e) {
		log.error("{} is occurred.", e.getErrorCode());

		return ResponseEntity.badRequest()
			.body(new JourneyErrorResponse(e.getErrorCode(), e.getErrorMessage()));
	}
}
