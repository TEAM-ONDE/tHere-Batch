package onde.there_batch.domain.type;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public enum RegionType {
	SEOUL("서울"),
	BUSAN("부산"),
	DAEGU("대구"),
	INCHEON("인천"),
	GWANGJU("광주"),
	DAEJEON("대전"),
	ULSAN("울산"),
	SEJONG("세종"),
	GYEONGGI("경기"),
	GANGWON("강원"),
	CHUNGBUK("충북"),
	CHUNGNAM("충남"),
	JEONBUK("전북"),
	JEONNAM("전남"),
	GYEONGBUK("경북"),
	GYEONGNAM("경남"),
	JEJU("제주");


	private String regionName;

//	public static RegionType findByRegion(String input) {
//		return Arrays.stream(RegionType.values())
//			.filter(type -> type.getRegionName().equals(input))
//			.findAny()
//			.orElseThrow(() -> new JourneyException(
//				JourneyErrorCode.NO_REGION_MATCHES));
//	}
}
