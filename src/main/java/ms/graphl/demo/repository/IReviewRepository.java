package ms.graphl.demo.repository;

import java.util.List;

import ms.graphl.demo.domain.Episode;
import ms.graphl.demo.domain.Review;

public interface IReviewRepository {

	Review add(final Review review);

	List<Review> findByEpisode(Episode episode);

	List<Review> findAll();
}
