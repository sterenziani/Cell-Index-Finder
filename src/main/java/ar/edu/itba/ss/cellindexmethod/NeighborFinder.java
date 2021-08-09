package ar.edu.itba.ss.cellindexmethod;
import java.util.Map;
import java.util.Set;

public interface NeighborFinder {
	Map<Particle, Set<Particle>> findNeighbors(Input input) throws Exception;
}
