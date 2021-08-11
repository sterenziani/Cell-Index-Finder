package ar.edu.itba.ss.cellindexmethod;

public class Validator {
	public static boolean validateParticles(Input resp) throws Exception
	{
		for(Particle p1 : resp.getParticles())
		{
			for(Particle p2 : resp.getParticles())
			{
				if(p1.getId() < p2.getId())
				{
					if(p1.isOverlapping(p2, resp.getL(), resp.getM(), resp.getWallPeriod()))
						throw new Exception("ERROR! PARTICLES " +p1.getId() +" AND " +p2.getId() +" OVERLAP!");
					if(p1.isNeighbor(p2, resp.getL(), resp.getM(), resp.getWallPeriod(), resp.getRc())
							&& resp.getL()/resp.getM() <= resp.getRc() + p1.getRadius() + p2.getRadius())
					{
						throw new Exception("ERROR! PARTICLES " +p1.getId() +" AND " +p2.getId()
						+" DEFY (L/M > rc + r1 + r2) RULE! Please use a smaller M or smaller radiuses. "
						+"r1 = " +p1.getRadius() +", r2 = " +p2.getRadius());
					}
				}
			}
		}
		return true;
	}
}
