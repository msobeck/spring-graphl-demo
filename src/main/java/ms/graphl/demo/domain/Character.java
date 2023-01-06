package ms.graphl.demo.domain;

import java.util.List;

public interface Character {

	String getId();

	String getName();

	List<String> getFriends();

	List<Episode> getAppearsIn();
}
