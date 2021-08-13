package ar.edu.itba.ss.cellindexmethod;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CellIndexFinder implements NeighborFinder{
	
	@Override
	public Map<Particle, Set<Particle>> findNeighbors(Input input) throws Exception
	{
        // Put particles inside the matrix as Particles
        Cell[][] matrix = new Cell[input.getM()][input.getM()];
        double l = input.getL()/input.getM();
        for(int i=0; i < input.getM(); i++)
        	for(int j=0; j < input.getM(); j++)
        		matrix[i][j] = new Cell();
        
        Map<Particle, Set<Particle>> map = new HashMap<>();
        for(Particle p : input.getParticles())
        {
        	int row = (int) (p.getY() / l);
        	int col = (int) (p.getX() / l);
        	matrix[row][col].addParticle(p);
        	map.put(p, new HashSet<>());
        }
        // Find neighbors
        for(int i=0; i < input.getM(); i++)
        {
        	for(int j=0; j < input.getM(); j++)
        	{
    			for(Particle p1 : matrix[i][j].getParticles())
    			{
    				// Get index of each cell to visit
    				int upperRow = i-1;
    				int lowerRow = i+1;
    				int rightCol = j+1;
    				if(input.getWallPeriod())
    				{
    					// Double mod to convert negative index
    					upperRow = (upperRow % input.getM() + input.getM()) % input.getM();
    					lowerRow = (lowerRow % input.getM() + input.getM()) % input.getM();
    					rightCol = (rightCol % input.getM() + input.getM()) % input.getM();
    				}
    				
    				// Look for neighbors in this cell
    				for(Particle p2 : matrix[i][j].getParticles())
    				{
    					if(p1.getId() < p2.getId() && p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
    					{
    						map.get(p1).add(p2);
    						map.get(p2).add(p1);
    					}
    				}
    				// Check neighbors in Upper Cell, if reachable
    				if(upperRow >= 0)
    				{
        				for(Particle p2 : matrix[upperRow][j].getParticles())
        				{
        					if(!p1.equals(p2) && p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						map.get(p1).add(p2);
        						map.get(p2).add(p1);
        					}
        				}
    				}

    				// Check neighbors in Upper-Right Cell, if reachable
    				if(upperRow >= 0 && rightCol < input.getM())
    				{
        				for(Particle p2 : matrix[upperRow][rightCol].getParticles())
        				{
        					if(!p1.equals(p2) && p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						map.get(p1).add(p2);
        						map.get(p2).add(p1);
        					}
        				}
    				}
    				
    				// Check neighbors in Right Cell, if reachable
    				if(rightCol < input.getM())
    				{
        				for(Particle p2 : matrix[i][rightCol].getParticles())
        				{
        					if(!p1.equals(p2) && p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						map.get(p1).add(p2);
        						map.get(p2).add(p1);
        					}
        				}
    				}
    				
    				// Check neighbors in Lower-Right cell, if reachable
    				if(rightCol < input.getM() && lowerRow < input.getM())
    				{
        				for(Particle p2 : matrix[lowerRow][rightCol].getParticles())
        				{
        					if(!p1.equals(p2) && p1.isNeighbor(p2, input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						map.get(p1).add(p2);
        						map.get(p2).add(p1);
        					}
        				}
    				}
    			}
        	}
        }
        return map;
	}
}
