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
    			for(CIMParticle p : matrix[i][j].getList())
    			{
    				// TODO: Check neighbors in same cell (should check if neighbors)
    				// TODO: Check neighbors in North
    				// TODO: Check neighbors in NE
    				// TODO: Check neighbors in East
    				// TODO: Check neighbors in SE
    			}
        	}
        }
        return null;
	}
}
