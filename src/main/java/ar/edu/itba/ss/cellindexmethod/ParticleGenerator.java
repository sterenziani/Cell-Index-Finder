package ar.edu.itba.ss.cellindexmethod;

import java.util.Map;

public class ParticleGenerator {

    private static ParticleGenerator particleGenerator;

    private ParticleGenerator() {

    }

    public static ParticleGenerator getInstance() {
        if(particleGenerator == null){
            particleGenerator = new ParticleGenerator();
        }
        return particleGenerator;
    }

    public void generateRandomPoints(int N, double L, Map<Long, Double> particleRadiusesMap, Map<Long, Point> particlePositionsMap){
        for(long i=0; i < N; i++) {
            double x=0;
            double y=0;
            double distance = -1;
            boolean checkNoOverlap = true;
            while(distance < 0) {
                x = Math.random()*L;
                y = Math.random()*L;
                distance = 0;
                double x1, y1;
                // Make sure it doesn't overlap any other point
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
                    }
                }
            }
            particlePositionsMap.put(i+1, new Point(x, y));
        }
    }
}
