package ar.edu.itba.ss.cellindexmethod;

public class CellIndexFinder implements NeighborFinder{
	
	@Override
	public Output findNeighbors(Input input) throws Exception
	{
        // Put particles inside the matrix as CIMParticles
		if(input.getM() < 3)
			throw new Exception("ERROR! M NEEDS TO BE LARGER THAN 3!");
        Cell[][] matrix = new Cell[input.getM()][input.getM()];
        double l = input.getL()/input.getM();
        for(int i=0; i < input.getM(); i++)
        	for(int j=0; j < input.getM(); j++)
        		matrix[i][j] = new Cell();
        
        for(Particle p : input.getParticles())
        {
        	int row = (int) (p.getY() / l);
        	int col = (int) (p.getX() / l);
        	matrix[row][col].addParticle(p);
        }
        
        // Find neighbors
        for(int i=0; i < input.getM(); i++)
        {
        	for(int j=0; j < input.getM(); j++)
        	{
    			for(CIMParticle p1 : matrix[i][j].getList())
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
    				for(CIMParticle p2 : matrix[i][j].getList())
    				{
    					if(!p1.equals(p2) && p1.getParticle().getId() < p2.getParticle().getId() && p1.getParticle().isOverlapping(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod()))
    						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" OVERLAP!");
    					if(!p1.equals(p2) && input.getL()/input.getM() <= input.getRc() + p1.getParticle().getRadius() + p1.getParticle().getRadius())
    						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" DEFY (L/M > rc + r1 + r2) RULE!");
    					if(!p1.equals(p2) && p1.getParticle().getId() < p2.getParticle().getId()
    							&& p1.getParticle().isNeighbor(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
    					{
    						p1.getNeighbors().add(p2.getParticle());
    						p2.getNeighbors().add(p1.getParticle());
    					}
    				}
    				// Check neighbors in Upper Cell, if reachable
    				if(upperRow >= 0)
    				{
        				for(CIMParticle p2 : matrix[upperRow][j].getList())
        				{
        					if(!p1.equals(p2) && p1.getParticle().isOverlapping(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod()))
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" OVERLAP!");
        					if(input.getL()/input.getM() <= input.getRc() + p1.getParticle().getRadius() + p1.getParticle().getRadius())
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" DEFY (L/M > rc + r1 + r2) RULE!");
        					if(p1.getParticle().isNeighbor(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						p1.getNeighbors().add(p2.getParticle());
        						p2.getNeighbors().add(p1.getParticle());
        					}
        				}
    				}

    				// Check neighbors in Upper-Right Cell, if reachable
    				if(upperRow >= 0 && rightCol < input.getM())
    				{
        				for(CIMParticle p2 : matrix[upperRow][rightCol].getList())
        				{
        					if(!p1.equals(p2) && p1.getParticle().isOverlapping(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod()))
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" OVERLAP!");
        					if(input.getL()/input.getM() <= input.getRc() + p1.getParticle().getRadius() + p1.getParticle().getRadius())
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" DEFY (L/M > rc + r1 + r2) RULE!");
        					if(p1.getParticle().isNeighbor(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						p1.getNeighbors().add(p2.getParticle());
        						p2.getNeighbors().add(p1.getParticle());
        					}
        				}
    				}
    				
    				// Check neighbors in Right Cell, if reachable
    				if(rightCol < input.getM())
    				{
        				for(CIMParticle p2 : matrix[i][rightCol].getList())
        				{
        					if(!p1.equals(p2) && p1.getParticle().isOverlapping(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod()))
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" OVERLAP!");
        					if(input.getL()/input.getM() <= input.getRc() + p1.getParticle().getRadius() + p1.getParticle().getRadius())
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" DEFY (L/M > rc + r1 + r2) RULE!");
        					if(p1.getParticle().isNeighbor(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						p1.getNeighbors().add(p2.getParticle());
        						p2.getNeighbors().add(p1.getParticle());
        					}
        				}
    				}
    				
    				// Check neighbors in Lower-Right cell, if reachable
    				if(rightCol < input.getM() && lowerRow < input.getM())
    				{
        				for(CIMParticle p2 : matrix[lowerRow][rightCol].getList())
        				{
        					if(!p1.equals(p2) && p1.getParticle().isOverlapping(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod()))
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" OVERLAP!");
        					if(input.getL()/input.getM() <= input.getRc() + p1.getParticle().getRadius() + p1.getParticle().getRadius())
        						throw new Exception("ERROR! PARTICLES " +p1.getParticle().getId() +" AND " +p2.getParticle().getId() +" DEFY (L/M > rc + r1 + r2) RULE!");
        					if(p1.getParticle().isNeighbor(p2.getParticle(), input.getL(), input.getM(), input.getWallPeriod(), input.getRc()))
        					{
        						p1.getNeighbors().add(p2.getParticle());
        						p2.getNeighbors().add(p1.getParticle());
        					}
        				}
    				}
    			}
        	}
        }
        
        // Print lists of neighbors
        for(int i=0; i < input.getM(); i++)
        {
        	for(int j=0; j < input.getM(); j++)
        	{
        		for(CIMParticle p : matrix[i][j].getList())
        		{
        			System.out.print(p.getParticle().getId() +" -> \t");
        			for(Particle n : p.getNeighbors())
        				System.out.print(n.getId() +" ");
        			System.out.println();
        		}
        	}
        }
        return null;
	}
}
