package ar.edu.itba.ss.cellindexmethod;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BruteForceFinder implements NeighborFinder{

	@Override
	public Map<Particle, List<Particle>> findNeighbors(Input input)
	{
		List<Pair> neighbors = new LinkedList<>();

		for(Particle p1 : input.getParticles())
		{
			for(Particle p2 : input.getParticles())
			{
				if(!p1.equals(p2) && p1.getId() < p2.getId())
				{
					if(p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()) && !neighbors.contains(new Pair(p1, p2))
							&& !neighbors.contains(new Pair(p2, p1)) )
					{
								neighbors.add(new Pair(p1, p2));
					}
				}
			}
		}
		return null;
	}

}
