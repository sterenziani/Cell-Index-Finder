package ar.edu.itba.ss.cellindexmethod;

public class CellIndexFinder implements NeighborFinder{
	
	@Override
	public Output findNeighbors(Input input)
	{
		// TODO: Error if two particles share position
        // Put particles inside the matrix as CIMParticles
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
        
        for(int i=0; i < input.getM(); i++)
        {
        	for(int j=0; j < input.getM(); j++)
        	{
    			for(CIMParticle p1 : matrix[i][j].getList())
    			{
    				// Look for neighbors in this cell
    				for(CIMParticle p2 : matrix[i][j].getList())
    				{
    					if(!p1.equals(p2) && p1.getParticle().getId() < p2.getParticle().getId()
    							&& p1.getParticle().isNeighbor(p2.getParticle(), input.getRc()))
    					{
    						p1.getNeighbors().add(p2.getParticle());
    						p2.getNeighbors().add(p1.getParticle());
    					}
    				}
    				// Check neighbors in Upper Cell
    				int upperRow = i-1;
    				if(upperRow > 0 || input.getWallPeriod())
    				{
    					// Loop to other side
    					upperRow = upperRow % input.getM();
        				for(CIMParticle p2 : matrix[upperRow][j].getList())
        				{
        					if(p1.getParticle().isNeighbor(p2.getParticle(), input.getRc()))
        					{
        						p1.getNeighbors().add(p2.getParticle());
        						p2.getNeighbors().add(p1.getParticle());
        					}
        				}
    				}

    				// TODO: Check neighbors in Upper-Right Cell
    				// TODO: Check neighbors in Right Cell
    				// TODO: Check neighbors in Lower-Right Cell
    			}
        	}
        }
        return null;
	}
}
