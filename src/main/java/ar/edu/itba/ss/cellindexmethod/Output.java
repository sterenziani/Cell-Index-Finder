package ar.edu.itba.ss.cellindexmethod;
import java.util.List;
import java.util.Map;

public class Output {
	private Map<Long, List<Particle>> map;

	public Output(Map<Long, List<Particle>> map) {
		this.map = map;
	}

	public Map<Long, List<Particle>> getMap() {
		return map;
	}
}
