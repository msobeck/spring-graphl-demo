package ms.graphl.demo.domain;

import java.util.List;

import lombok.Data;

@Data
public class Droid implements Character {

	private String id;

	private String type;

	private String name;

	private String primaryFunction;

	private List<String> friends;

	private List<Episode> appearsIn;
}
