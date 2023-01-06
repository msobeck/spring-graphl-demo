package ms.graphl.demo.repository;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ms.graphl.demo.domain.Droid;
import ms.graphl.demo.domain.Human;
import ms.graphl.demo.domain.Starship;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class DataImporter implements ApplicationRunner {

	private final ICharacterRepository characterRepository;

	private final IStarshipRepository starshipRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		final var humansJson = load("Humans.json");
		final var humans = this.objectMapper.readValue(humansJson, new TypeReference<List<Human>>() {
		});

		final var droidsJson = load("Droids.json");
		final var droids = this.objectMapper.readValue(droidsJson, new TypeReference<List<Droid>>() {
		});

		this.characterRepository.saveAll(Stream.concat(humans.stream(), droids.stream())
				.collect(toList()));

		final var starshipsJson = load("Starships.json");
		final var starships = this.objectMapper.readValue(starshipsJson, new TypeReference<List<Starship>>() {
		});

		this.starshipRepository.saveAll(starships);
	}

	@SneakyThrows
	private static String load(final String fileName) {
		final var resource = getResource(fileName);
		return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
	}

	private static Resource getResource(final String fileName) {
		return new ClassPathResource("/data/starwars/" + fileName);
	}
}
