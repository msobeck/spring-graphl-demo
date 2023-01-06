package ms.graphl.demo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ms.graphl.demo.domain.Character;
import ms.graphl.demo.domain.Episode;

import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class CharacterRepository implements ICharacterRepository {

	private final List<Character> characters = new ArrayList<>();

	@Override
	public void saveAll(final Collection<Character> characters) {
		this.characters.addAll(characters);
	}

	@Override
	public List<Character> findAll() {
		return new ArrayList<>(this.characters);
	}

	@Override
	public List<Character> findByEpisode(final Episode episode) {
		return this.characters.stream()
				.filter(c -> c.getAppearsIn().contains(episode))
				.collect(toList());
	}

	@Override
	public List<Character> findByIds(final Collection<String> ids) {
		return this.characters.stream()
				.filter(c -> ids.contains(c.getId()))
				.collect(toList());
	}

	@Override
	public <T extends Character> Optional<T> findByIdAndType(final String id, final Class<T> clazz) {
		return this.characters.stream()
				.filter(character -> id.equals(character.getId()))
				.filter(character -> character.getClass().equals(clazz))
				.map(clazz::cast)
				.findFirst();
	}
}
