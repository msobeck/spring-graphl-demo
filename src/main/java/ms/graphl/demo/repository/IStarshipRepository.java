package ms.graphl.demo.repository;

import java.util.Collection;
import java.util.List;

import ms.graphl.demo.domain.Starship;

public interface IStarshipRepository {

	void saveAll(final Collection<Starship> characters);

	List<Starship> findByIds(final Collection<String> ids);

	List<Starship> findAll();
}
