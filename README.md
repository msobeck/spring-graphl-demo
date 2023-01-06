# Spring GraphQL Demo

This project is a Spring GraphQL Server implementation that is compatible with the Star
Wars example types from the
[GraphQL Documentation](https://graphql.org/learn/queries/). You will be able to run most
of the star wars related
queries and mutations from the documentation.

## Run the project

from the command line:

`mvn spring-boot:run`

After start, open http://localhost:8080/ in your browser to access the
playground [GraphiQL](https://github.com/graphql/graphiql)

## Examples

### Queries

### [Inline Fragments](https://graphql.org/learn/queries/#inline-fragments)

Request

```
query {
  hero(episode: JEDI) {
    name
    ... on Droid {
      primaryFunction
    }
    ... on Human {
      height
    }
  }
}
```

Response

```
{
  "data": {
    "hero": {
      "name": "Luke Skywalker",
      "height": 1.72
    }
  }
}
```

### [Mutations](https://graphql.org/learn/queries/#mutations)

Request

```
mutation {
  createReview(ep: JEDI, review: {
    stars: 5,
    commentary: "This is a great movie!"
  }) {
   # return only the id
    id
  } 
}
```

Response

```
{
  "data": {
    "createReview": {
      "id": "1"
    }
  }
}
```

## Sources

- https://www.graphql-java.com/documentation/getting-started
- https://docs.spring.io/spring-graphql/docs/current/reference/html/
- https://github.com/nilshartmann