package ms.graphl.demo.web;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.graphl.demo.domain.Episode;
import ms.graphl.demo.domain.Review;
import ms.graphl.demo.repository.ReviewRepository;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
class ReviewController {

	private final ReviewRepository reviewRepository;

	@MutationMapping
	public Review createReview(
			@Argument("ep") final Episode episode,
			@Argument("review") @Valid final ReviewInput reviewInput) {

		return this.reviewRepository.add(Review.builder()
				.episode(episode)
				.commentary(reviewInput.commentary())
				.stars(reviewInput.stars())
				.build());
	}

	@SchemaMapping(typeName = "Query")
	public List<Review> reviews(
			@Argument("ep") final Episode episode) {
		if (episode == null) {
			return this.reviewRepository.findAll();
		}

		return this.reviewRepository.findByEpisode(episode);
	}
}

