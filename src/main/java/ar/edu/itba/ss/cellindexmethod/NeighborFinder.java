package ar.edu.itba.ss.cellindexmethod;

import java.util.List;
import java.util.Map;

public interface NeighborFinder {
	Map<Particle, List<Particle>> findNeighbors(Input input) throws Exception;
}
