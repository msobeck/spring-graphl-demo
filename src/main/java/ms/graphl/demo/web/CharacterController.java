package ms.graphl.demo.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.graphl.demo.domain.Character;
import ms.graphl.demo.domain.Droid;
import ms.graphl.demo.domain.Episode;
import ms.graphl.demo.domain.Human;
import ms.graphl.demo.domain.Starship;
import ms.graphl.demo.domain.Unit;
import ms.graphl.demo.domain.service.SearchService;
import ms.graphl.demo.repository.ICharacterRepository;
import ms.graphl.demo.repository.StarshipRepository;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static java.util.stream.Collectors.toList;

@Slf4j
@Controller
@RequiredArgsConstructor
class CharacterController {

	private final static float METER_TO_FOOT_MULTIPLIER = 3.28084f;

	private final ICharacterRepository characterRepository;

	private final StarshipRepository starshipRepository;

	private final SearchService searchService;


	@QueryMapping
	public Character hero(@Argument final Episode episode) {
		if (episode == null) {
			return this.characterRepository.findAll().stream()
					.findFirst()
					.orElse(null);
		}

		return this.characterRepository.findByEpisode(episode).stream()
				.findFirst()
				.orElse(null);
	}

	@QueryMapping
	public List<Character> heros() {
		return this.characterRepository.findAll();
	}

	@QueryMapping
	public Human human(@Argument final String id) {
		return this.characterRepository.findByIdAndType(id, Human.class)
				.orElse(null);
	}

	@QueryMapping
	public Droid droid(@Argument final String id) {
		return this.characterRepository.findByIdAndType(id, Droid.class)
				.orElse(null);
	}


	// The specific typeName must be listed, because the superclass "Character" is not mappable
	// So methods must be duplicated for all subclasses.
	@SchemaMapping(typeName = "Human", field = "friends")
	public List<Character> friendsForHuman(final Character character) {
		return this.findFriends(character);
	}

	@SchemaMapping(typeName = "Droid", field = "friends")
	public List<Character> friendsForDroid(final Character character) {
		return this.findFriends(character);
	}

	private List<Character> findFriends(final Character character) {
		return this.characterRepository.findByIds(character.getFriends());
	}

	@SchemaMapping(typeName = "Human", field = "height")
	public Float humanHeight(final Human human, @Argument final Unit unit) {
		if (human.getHeight() == null) {
			return null;
		}

		if (unit == null || unit == Unit.METER) {
			return human.getHeight();
		}

		return human.getHeight() * METER_TO_FOOT_MULTIPLIER;
	}

	@SchemaMapping(typeName = "Query")
	public List<Character> searchHero(@Argument final String name) {
		if (name == null) {
			return this.characterRepository.findAll();
		}

		return this.characterRepository.findAll()
				.stream()
				.filter(c -> c.getName().contains(name))
				.collect(toList());
	}

	@BatchMapping(typeName = "Human")
	public Map<Human, List<Starship>> starships(final List<Human> humans) {
		final var distinctStarshipIds = humans.stream()
				.map(Human::getStarShips)
				.flatMap(Collection::stream)
				.distinct()
				.collect(toList());

		final var starships = this.starshipRepository.findByIds(distinctStarshipIds);

		final Map<Human, List<Starship>> result = new HashMap<>();

		for (final var human : humans) {
			var ships = starships.stream()
					.filter(ship -> human.getStarShips().contains(ship.id()))
					.collect(toList());

			if (ships.isEmpty()) {
				ships = null; // starships are optional, so set empty list to null
			}
			result.put(human, ships);
		}

		return result;
	}

	// alternative without batch data fetching
	// https://www.graphql-java.com/documentation/data-fetching
/*  @SchemaMapping(typeName = "Human", field = "starships")
  public List<Starship> starships(final Human human) {
    if (human.getStarShips() == null || human.getStarShips().isEmpty()) {
      return null;
    }

    return this.starshipRepository.findByIds(human.getStarShips());
  }*/

	@SchemaMapping(typeName = "Starship", field = "length")
	public Float starshipLength(final Starship starship, @Argument final Unit unit) {
		if (starship.length() == null) {
			return null;
		}

		if (unit == null || unit == Unit.METER) {
			return starship.length();
		}

		return starship.length() * METER_TO_FOOT_MULTIPLIER;
	}

	// Currently disabled because the correct java implementation is missing:
	// Can't resolve '/search'. Abstract type 'SearchResult' must resolve to an Object type at runtime for field 'Query.search'.
	// Could not determine the exact type of 'SearchResult'
/*  @SchemaMapping(typeName = "Query")
  public List<Object> search(@Argument final String text) {
    if (text == null) {
      return null;
    }

    return this.searchService.search(text);
  }*/
}

