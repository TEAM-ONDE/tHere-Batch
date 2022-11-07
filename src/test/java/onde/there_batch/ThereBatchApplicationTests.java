package onde.there_batch;

import java.time.LocalDate;
import onde.there_batch.domain.Journey;
import onde.there_batch.domain.JourneyTheme;
import onde.there_batch.domain.Member;
import onde.there_batch.domain.Place;
import onde.there_batch.domain.PlaceHeart;
import onde.there_batch.domain.type.JourneyThemeType;
import onde.there_batch.domain.type.PlaceCategoryType;
import onde.there_batch.domain.type.RegionType;
import onde.there_batch.redies.RedisService;
import onde.there_batch.repository.CommentRepository;
import onde.there_batch.repository.JourneyBookmarkRepository;
import onde.there_batch.repository.JourneyRepository;
import onde.there_batch.repository.JourneyThemeRepository;
import onde.there_batch.repository.MemberRepository;
import onde.there_batch.repository.PlaceHeartRepository;
import onde.there_batch.repository.PlaceImageRepository;
import onde.there_batch.repository.PlaceRepository;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThereBatchApplicationTests {

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

	@Test
	void aa() {
		for (int i = 0; i < 100; i++) {
			Journey journey = journeyRepository.save(new Journey());
			redisService.setListOps("journeyId", String.valueOf(journey.getId()));
			JourneyTheme journeyTheme = journeyThemeRepository.save(JourneyTheme.builder().journey(journey).build());
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
	void 생성() {
		//given
		Member member = memberRepository.save(Member.builder()
				.id("ooliskoo@naver.com")
				.email("ooliskoo@naver.com")
				.password("1234")
				.name("김이안")
				.nickName("ian")
				.profileImageUrl("any url")
			.build());
		Journey journey = journeyRepository.save(Journey.builder()
				.member(member)
				.title("journey title")
				.startDate(LocalDate.now())
				.endDate(LocalDate.now())
				.journeyThumbnailUrl("any url")
				.disclosure("disclosure")
				.introductionText("journey text")
				.numberOfPeople(2)
				.region(RegionType.BUSAN)
			.build());
		JourneyTheme journeyTheme = journeyThemeRepository.save(JourneyTheme.builder()
				.journey(journey)
				.journeyThemeName(JourneyThemeType.CITY)
			.build());
		Place place = placeRepository.save(Place.builder()
				.latitude(123.0)
				.longitude(32.0)
				.title("place title")
				.text("place text")
				.addressName("address")
				.placeCategory(PlaceCategoryType.ACCOMMODATION)
				.journey(journey)
				.placeHeartCount(2L)
			.build());
		PlaceHeart placeHeart = placeHeartRepository.save(PlaceHeart.builder()
				.place(place)
				.member(member)
			.build());
        //when
		//when

		//then
	}
}
