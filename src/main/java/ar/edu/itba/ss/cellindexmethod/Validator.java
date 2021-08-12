package ar.edu.itba.ss.cellindexmethod;

public class Validator {
	public static boolean validateParticles(Input input) throws Exception
	{
		for(Particle p1 : input.getParticles())
		{
			for(Particle p2 : input.getParticles())
			{
				if(p1.getId() < p2.getId())
				{
					if(p1.isOverlapping(p2, input.getL(), input.getM(), input.getWallPeriod()))
						throw new Exception("ERROR! PARTICLES " +p1.getId() +" AND " +p2.getId() +" OVERLAP!");
					if(p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc())
							&& input.getL()/input.getM() <= input.getRc() + p1.getRadius() + p2.getRadius())
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
	
	public static int getBestM(Input input)
	{
		double r1 = 0;
		double r2 = 0;
		for(Particle p : input.getParticles())
		{
			if(p.getRadius() >= r1){
				r2 = r1;
				r1 = p.getRadius();
			}
			else if(p.getRadius() > r2)
				r2 = p.getRadius();
		}
		double limit = input.getL()/(input.getRc() + r1 + r2);
		for(int M = 1+(int)limit; M > 0; M--)
		{
			if(input.getL()/M > (input.getRc() + r1 + r2))
				return M;
		}
		return -1;
	}
}
