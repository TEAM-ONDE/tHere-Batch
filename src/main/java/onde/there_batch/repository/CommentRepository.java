package onde.there_batch.repository;

import onde.there_batch.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	void deleteAllByPlaceId(Long placeId);
}
