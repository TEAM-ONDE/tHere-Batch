package onde.there_batch.domain.type;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JourneyThemeType {
	HEALING("힐링"),
	RESTAURANT("식도락"),
	LEISURE("레저"),
	CITY("도시"),
	CULTURE("문화"),
	CAMPING("캠핑"),
	KIDS("키즈"),
	COST_EFFECTIVENESS("가성비"),
	PET("반려동물"),
	ECT("기타"),
	EMPTY("없음");

	private final String themeName;

//	public static JourneyThemeType findByTheme(String input) {
//		return Arrays.stream(JourneyThemeType.values())
//			.filter(type -> type.getThemeName().equals(input))
//			.findAny()
//			.orElseThrow(() -> new JourneyException(
//				JourneyErrorCode.THERE_IS_NO_MATCHING_THEME));
//	}
}
