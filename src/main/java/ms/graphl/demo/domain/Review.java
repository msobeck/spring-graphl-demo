package ms.graphl.demo.domain;

import lombok.Builder;

@Builder
public record Review(
		String id,
		Episode episode,
		int stars,
		String commentary
) {

}
