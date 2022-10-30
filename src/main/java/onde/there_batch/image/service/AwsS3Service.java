package onde.there_batch.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.repository.PlaceImageRepository;
import onde.there_batch.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

	private final AmazonS3 amazonS3;
	private final PlaceRepository placeRepository;
	private final PlaceImageRepository placeImageRepository;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	@Value("${cloud.aws.baseUrl}")
	private String baseUrl;

	public void deleteFile(String url) {
		log.info("이미지 S3에서 삭제 시작! (url : " + url + ")");
		String fileName = url.replaceAll(baseUrl, "");
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
		log.info("이미지 S3에서 삭제 끝! (url : " + url + ")");
	}

}
