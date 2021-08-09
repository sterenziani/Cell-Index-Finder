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
        start = Instant.now();
        NeighborFinder finder = new CellIndexFinder();
        Map<Particle, List<Particle>> map = runFinder(finder, input);
        end = Instant.now();
        // Print lists of neighbors
        printNeighbors(map);
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Execution finished in " +timeElapsed.toMillis() +" ms");

        start = Instant.now();
        finder = new BruteForceFinder();
        map = runFinder(finder, input);
        end = Instant.now();
        // Print lists of neighbors
        printNeighbors(map);
        timeElapsed = Duration.between(start, end);
        System.out.println("Execution finished in " +timeElapsed.toMillis() +" ms");
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
			return Parser.getInstance().parseMock(args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
