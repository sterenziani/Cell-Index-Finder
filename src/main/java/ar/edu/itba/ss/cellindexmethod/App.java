package ar.edu.itba.ss.cellindexmethod;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class App
{
    public static void main( String[] args )
    {
        Instant start, end;
        Input input = getInput(args);
        System.out.println("Files parsed!");
        if(input != null){
        	// Brute Force
            start = Instant.now();
            NeighborFinder finder = new BruteForceFinder();
            Map<Particle, List<Particle>> map = runFinder(finder, input);
            end = Instant.now();
            printNeighbors(map);
            Duration timeElapsed = Duration.between(start, end);
            System.out.println("Brute Force Execution finished in " +timeElapsed.toMillis() +" ms\n\n\n");
            
            // Cell Index Method
            start = Instant.now();
            finder = new CellIndexFinder();
            map = runFinder(finder, input);
            end = Instant.now();
            printNeighbors(map);
            timeElapsed = Duration.between(start, end);
            System.out.println("CellIndexFinder Execution finished in " +timeElapsed.toMillis() +" ms");
        }
    }

    private static Map<Particle, List<Particle>> runFinder( NeighborFinder finder, Input input) {
        Map<Particle, List<Particle>> map = null;
        try{
            map = finder.findNeighbors(input);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return map;
    }

    private static void printNeighbors( Map<Particle, List<Particle>> map ) {
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
    }

    private static Input getInput(String[] args) {
    	try {
			return Parser.getInstance().parse(args);
		} catch (Exception e) {
            System.out.println(e.getMessage());
			return null;
		}
    }
}
