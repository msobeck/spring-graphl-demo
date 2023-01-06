package ms.graphl.demo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ms.graphl.demo.domain.Character;
import ms.graphl.demo.domain.Episode;

public interface ICharacterRepository {

	void saveAll(final Collection<Character> characters);

	List<Character> findAll();

	List<Character> findByEpisode(final Episode episode);

	List<Character> findByIds(final Collection<String> ids);

	<T extends Character> Optional<T> findByIdAndType(final String id, final Class<T> clazz);
}
