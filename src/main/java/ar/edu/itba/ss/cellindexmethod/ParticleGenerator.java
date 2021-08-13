package ar.edu.itba.ss.cellindexmethod;

import java.time.Instant;
import java.util.Map;

public class ParticleGenerator {

    private static ParticleGenerator particleGenerator;
	private static final double RANDOM_MULTIPLIER = 0.8;

    private ParticleGenerator() {

    }

    public static ParticleGenerator getInstance() {
        if(particleGenerator == null)
            particleGenerator = new ParticleGenerator();
        return particleGenerator;
    }
    
    public boolean generateRandomRadiuses(int N, double L, int M, double rc, Map<Long, Double> particleRadiusesMap, int timeout)
    {
    	if(M <= 0)
    		M = 15;
    	Instant timeLimit = Instant.now().plusMillis(timeout);
    	particleRadiusesMap.put((long) 1, Math.random()*(L/M - rc)*RANDOM_MULTIPLIER);
		for(long j=2; j <= N; j++)
		{
			double r2 = Math.random()*(L/M - rc)/4;
			for(long i=1; i < j; i++)
			{
				double r1 = particleRadiusesMap.get(i);
				while(L/M <= rc + r1 + r2)
				{
	            	if(Instant.now().isAfter(timeLimit))
	            		return false;
					r2 = Math.random()*(L/M - rc - r1);
				}
			}
			particleRadiusesMap.put(j, r2);
		}
		return true;
    }

    public boolean generateRandomPoints(int N, double L, Map<Long, Double> particleRadiusesMap, Map<Long, Point> particlePositionsMap, int timeout)
    {
    	Instant timeLimit = Instant.now().plusMillis(timeout);
    	for(long i=0; i < N; i++) {
            double x=0;
            double y=0;
            double distance = -1;
            boolean checkNoOverlap = true;
            while(distance < 0) {
            	if(Instant.now().isAfter(timeLimit))
            		return false;
                x = Math.random()*L;
                y = Math.random()*L;
                distance = 0;
                double x1, y1;
                if(checkNoOverlap)
                {
                    for(Long k : particlePositionsMap.keySet()) {
                        x1 = x; y1 = y;
                        Point p2 = particlePositionsMap.get(k);
                        double x2 = p2.getX();
                        double y2 = p2.getY();
                        if(x1 < x2 && Math.abs(x1-x2) > Math.abs(L+x1-x2))
                            x1 += L;
                        else if(x2 < x1 && Math.abs(x1-x2) > Math.abs(x1-x2-L))
                            x2 += L;
                        if(y1 < y2 && Math.abs(y1-y2) > Math.abs(L+y1-y2))
                            y1 += L;
                        else if(y2 < y1 && Math.abs(y1-y2) > Math.abs(y1-y2-L))
                            y2 += L;
                        double centerDistance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                        double newDistance = centerDistance - particleRadiusesMap.get(i+1) - particleRadiusesMap.get(k);
                        distance = Double.min(distance, newDistance);
                        if(distance < 0)
                        	break;
                    }
                }
            }
            particlePositionsMap.put(i+1, new Point(x, y));
        }
    	return true;
    }

    public boolean generateRandomParticles(int N, double L, int M, double rc, Map<Long, Double> particleRadiusesMap, Map<Long, Point> particlePositionsMap, int timeout)
    {
    	particleRadiusesMap.clear();
    	particlePositionsMap.clear();
    	Instant timeLimit = Instant.now().plusMillis(timeout);
    	for(long i=0; i < N; i++)
    	{
    		boolean uniquePoint = true;
            double x = Math.random()*L;
            double y = Math.random()*L;
            for(Long k : particlePositionsMap.keySet())
            {
            	if(Instant.now().isAfter(timeLimit))
            		return false;
            	if(x == particlePositionsMap.get(k).getX() || y == particlePositionsMap.get(k).getY())
            	{
            		i--;
            		uniquePoint = false;
            		break;
            	}
            }
            if(uniquePoint)
            {
            	particlePositionsMap.put(i+1, new Point(x, y));
            	particleRadiusesMap.put(i+1, 0.0);
            }
    	}
        double maxR = 0.0;
        if(M <= 0)
        	M = (int) (L/(rc*2));
    	for(long i=0; i < N; i++)
    	{
            double r = Math.random()*(L/M - rc);
            if(r > maxR)
            	maxR = r;
            for(Long k : particlePositionsMap.keySet())
            {
                double x1 = particlePositionsMap.get(i+1).getX();
                double y1 = particlePositionsMap.get(i+1).getY();
                Point p2 = particlePositionsMap.get(k);
                double x2 = p2.getX();
                double y2 = p2.getY();
                if(x1 < x2 && Math.abs(x1-x2) > Math.abs(L+x1-x2))
                    x1 += L;
                else if(x2 < x1 && Math.abs(x1-x2) > Math.abs(x1-x2-L))
                    x2 += L;
                if(y1 < y2 && Math.abs(y1-y2) > Math.abs(L+y1-y2))
                    y1 += L;
                else if(y2 < y1 && Math.abs(y1-y2) > Math.abs(y1-y2-L))
                    y2 += L;
                double centerDistance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                while(L/M <= rc + r + maxR || centerDistance < (r + particleRadiusesMap.get(k))){
                	if(Instant.now().isAfter(timeLimit))
                		return false;
                	r = Math.random()*r;            	
                }
                if(r > maxR)
                	maxR = r;
            }
            particleRadiusesMap.put(i+1, r);
    	}
		return true;
    }
}
