package ar.edu.itba.ss.cellindexmethod;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BruteForceFinder implements NeighborFinder{

	@Override
	public Map<Particle, List<Particle>> findNeighbors(Input input) throws Exception
	{
		Map<Particle, List<Particle>> neighbors = new HashMap<>();
		if(input.getM() < 3)
			throw new Exception("ERROR! M NEEDS TO BE LARGER THAN 3!");

		for(Particle p1 : input.getParticles())
		{
			for(Particle p2 : input.getParticles())
			{
				if(!p1.equals(p2) && p1.getId() < p2.getId())
				{
					if(p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
					{
						if( !neighbors.containsKey(p1)) {
							neighbors.put(p1, new LinkedList<>());
						}
						if(!neighbors.containsKey(p2)) {
							neighbors.put(p2, new LinkedList<>());
						}
						if( !neighbors.get(p1).contains(p2)){
							neighbors.get(p1).add(p2);
							neighbors.get(p2).add(p1);
						}
					}
				}
			}
		}
		return neighbors;
	}

}
