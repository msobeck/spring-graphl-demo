package ms.graphl.demo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ms.graphl.demo.domain.Starship;

import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class StarshipRepository implements IStarshipRepository {

	private final List<Starship> starships = new ArrayList<>();


	@Override
	public void saveAll(final Collection<Starship> starships) {
		this.starships.addAll(starships);
	}

	@Override
	public List<Starship> findByIds(final Collection<String> ids) {
		return this.starships.stream().filter(s -> ids.contains(s.id()))
				.collect(toList());
	}

	@Override
	public List<Starship> findAll() {
		return this.starships;
	}
}
