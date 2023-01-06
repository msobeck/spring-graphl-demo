package ms.graphl.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ms.graphl.demo.domain.Episode;
import ms.graphl.demo.domain.Review;

import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class ReviewRepository implements IReviewRepository {

	private final AtomicInteger reviewIds = new AtomicInteger(0);

	private final List<Review> reviews = new ArrayList<>();

	@Override
	public Review add(final Review review) {
		final var reviewToAdd = Review.builder()
				.id(Integer.toString(this.reviewIds.incrementAndGet()))
				.commentary(review.commentary())
				.episode(review.episode())
				.stars(review.stars())
				.build();
		this.reviews.add(reviewToAdd);

		return reviewToAdd;
	}

	@Override
	public List<Review> findByEpisode(final Episode episode) {
		return this.reviews.stream()
				.filter(r -> episode.equals(r.episode()))
				.collect(toList());
	}

	@Override
	public List<Review> findAll() {
		return this.reviews;
	}
}
