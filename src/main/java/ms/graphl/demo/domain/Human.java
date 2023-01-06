package ms.graphl.demo.domain;

import java.util.List;

import lombok.Data;

@Data
public class Human implements Character {

	private String id;

	private String name;

	private String homePlanet;

	private Float height;

	private List<String> friends;

	private List<Episode> appearsIn;

	private List<String> starShips;
}
