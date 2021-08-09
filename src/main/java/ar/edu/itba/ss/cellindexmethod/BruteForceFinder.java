package ar.edu.itba.ss.cellindexmethod;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BruteForceFinder implements NeighborFinder{

	@Override
	public Map<Particle, Set<Particle>> findNeighbors(Input input) throws Exception
	{
		Map<Particle, Set<Particle>> neighbors = new HashMap<>();
        for(Particle p : input.getParticles())
        	neighbors.put(p, new HashSet<>());
        
		for(Particle p1 : input.getParticles())
		{
			for(Particle p2 : input.getParticles())
			{
				if(p1.getId() < p2.getId())
				{
					if(p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
					{
						neighbors.get(p1).add(p2);
						neighbors.get(p2).add(p1);
					}
				}
			}
		}
		return neighbors;
	}

}
