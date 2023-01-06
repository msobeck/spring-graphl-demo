package ms.graphl.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;


@Configuration
class GraphQlConfig {
	@Bean
	RuntimeWiringConfigurer runtimeWiringConfigurer(final CharacterTypeResolver characterTypeResolver) {
		return wiringBuilder -> wiringBuilder
				.type("Character", typeWriting ->
						typeWriting
								.typeResolver(characterTypeResolver)
				);
	}
}

