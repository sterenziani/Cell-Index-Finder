package ar.edu.itba.ss.cellindexmethod;

public class BruteForceFinder implements NeighborFinder{

	@Override
	public Output findNeighbors(Input input)
	{
		for(Particle p1 : input.getParticles())
		{
			for(Particle p2 : input.getParticles())
			{
				if(!p1.equals(p2) && p1.getId() < p2.getId())
				{
					// TODO: Check if neighbors
				}
			}
		}
		return null;
	}

}
