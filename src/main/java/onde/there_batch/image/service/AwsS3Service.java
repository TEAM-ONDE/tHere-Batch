package onde.there_batch.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onde.there_batch.domain.Place;
import onde.there_batch.repository.PlaceImageRepository;
import onde.there_batch.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.baseUrl}")
	private String baseUrl;

	private final PlaceRepository placeRepository;
	private final PlaceImageRepository placeImageRepository;

	public void deleteFile(String url) {
		log.info("이미지 S3에서 삭제 시작! (url : " + url + ")");
		String fileName = url.replaceAll(baseUrl, "");
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
		log.info("이미지 S3에서 삭제 끝! (url : " + url + ")");
	}

}
