package ms.graphl.demo;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import ms.graphl.demo.domain.Droid;
import ms.graphl.demo.domain.Human;

import org.springframework.stereotype.Component;

@Component
class CharacterTypeResolver implements TypeResolver {

	@Override
	public GraphQLObjectType getType(final TypeResolutionEnvironment env) {
		final Object javaObject = env.getObject();
		if (javaObject instanceof Human) {
			return env.getSchema().getObjectType("Human");
		}
		else if (javaObject instanceof Droid) {
			return env.getSchema().getObjectType("Droid");
		}
		throw new RuntimeException("No GraphQL Type mapping present for class: " + javaObject.getClass());
	}
}
