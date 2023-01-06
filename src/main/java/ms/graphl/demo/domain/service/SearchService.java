package ms.graphl.demo.domain.service;

import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import ms.graphl.demo.repository.ICharacterRepository;
import ms.graphl.demo.repository.IStarshipRepository;

import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final ICharacterRepository characterRepository;

	private final IStarshipRepository starshipRepository;

	public List<Object> search(final String text) {
		final var characters = this.characterRepository.findAll().stream()
				.filter(c -> c.getName().contains(text))
				.toList();

		final var starships = this.starshipRepository.findAll().stream()
				.filter(starship -> starship.name().contains(text))
				.toList();

		return Stream.of(characters, starships)
				.collect(toList());
	}
}
