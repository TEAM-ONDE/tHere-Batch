package onde.there_batch.exception.member;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MemberException extends RuntimeException {

	private MemberErrorCode memberErrorCode;
	private String errorMessage;

	public MemberException(MemberErrorCode memberErrorCode) {
		this.memberErrorCode = memberErrorCode;
		this.errorMessage = memberErrorCode.getDescription();
	}
}
