package ar.edu.itba.ss.cellindexmethod;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class App
{
    public static void main( String[] args )
    {
        Input input = getInput();
        Instant start = Instant.now();
        NeighborFinder finder = new CellIndexFinder();
        Map<Particle, List<Particle>> map = null;
        try{
        	map = finder.findNeighbors(input);
		}
        catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        Instant end = Instant.now();
        
        // Print lists of neighbors
        if(map != null)
        {
            for(Particle p : map.keySet())
            {
            	System.out.print(p.getId() +" -> \t");
            	for(Particle n : map.get(p))
            		System.out.print(n.getId() +" ");
            	System.out.println();
            }
        }
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Execution finished in " +timeElapsed.toMillis() +" ms");
    }
    
    private static Input getInput() {
    	try {
			return Parser.getInstance().parseFiles("path");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
