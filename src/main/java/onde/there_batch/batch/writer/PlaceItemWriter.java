package onde.there_batch.batch.writer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.domain.PlaceImage;
import onde.there_batch.image.service.AwsS3Service;
import onde.there_batch.repository.CommentRepository;
import onde.there_batch.repository.PlaceHeartRepository;
import onde.there_batch.repository.PlaceHeartSchedulingRepository;
import onde.there_batch.repository.PlaceImageRepository;
import onde.there_batch.repository.PlaceRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
@Slf4j
public class PlaceItemWriter implements ItemWriter<Long> {

	private final PlaceRepository placeRepository;
	private final CommentRepository commentRepository;
	private final PlaceHeartRepository placeHeartRepository;
	private final PlaceHeartSchedulingRepository placeHeartSchedulingRepository;
	private final PlaceImageRepository placeImageRepository;
	private final AwsS3Service awsS3Service;


	@Override
	public void write(List<? extends Long> placeIds) throws Exception {
		if (placeIds.isEmpty()) {
			log.info("삭제할 장소가 없습니다.");
			return;
		}
		log.info("PlaceItemWriter 시작 사이즈 : " + placeIds.size());
		for (Long placeId : placeIds) {
			commentRepository.deleteAllByPlaceId(placeId);
			placeHeartRepository.deleteAllByPlaceId(placeId);
			placeHeartSchedulingRepository.deleteAllByPlaceId(placeId);
			List<PlaceImage> placeImages = placeImageRepository.findAllByPlaceId(placeId);
			placeImageRepository.deleteAllByPlaceId(placeId);
			placeRepository.deleteById(placeId);
			if (placeImages.isEmpty()) {
				log.info("placeId : " + placeId + "에 저장된 사진이 없습니다.");
				continue;
			}
			for (PlaceImage placeImage : placeImages) {
				awsS3Service.deleteFile(placeImage.getUrl());
			}
		}
		log.info("장소, 댓글, 좋아요, 이미지 삭제 완료");
	}
}
