package ar.edu.itba.ss.cellindexmethod;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

public class App
{
	private final static String OUTPUT_FILE = "output.txt";
    private static final String OUTPUT_NEIGHBORS_BRUTE = "output_neighbors_brute.csv";
    private static final String OUTPUT_NEIGHBORS_CMI = "output_neighbors_cmi.csv";

    public static void main( String[] args ) throws IOException
    {
        Instant start, end;
        Input input = getInput(args);
        if(input != null){
            System.out.println("Files parsed!");
            InputToCSV inputToCSV = InputToCSV.getInstance();
            if(!inputToCSV.printToCSV(input)){
                System.out.println("ERROR: Could not print input to CSV");
            } else {

                // Brute Force
                start = Instant.now();
                NeighborFinder finder = new BruteForceFinder();
                Map<Particle, Set<Particle>> map = runFinder(finder, input);
                end = Instant.now();
                //printNeighbors(map);
                Duration timeElapsed = Duration.between(start, end);
                System.err.println("Brute Force Execution finished in " + timeElapsed.toMillis() + " ms\n\n\n");
                OutputToCSV outputToCSV = OutputToCSV.getInstance();
                outputToCSV.printToCSV(map, OUTPUT_NEIGHBORS_BRUTE);
                
                // Cell Index Method
                finder = new CellIndexFinder();
                start = Instant.now();
                map = runFinder(finder, input);
                end = Instant.now();
                //printNeighbors(map);
                timeElapsed = Duration.between(start, end);
                System.err.println("CellIndexFinder Execution finished in " + timeElapsed.toMillis() + " ms");
                outputNeighbors(map);
                outputToCSV.printToCSV(map, OUTPUT_NEIGHBORS_CMI);
                
                // Test
                int i=0;
                System.out.println("What's the best M:");
                try {
					while(Validator.validateParticles(input))
					{
						finder = new CellIndexFinder();
						i++;
						input.setM(i);
					    start = Instant.now();
					    map = runFinder(finder, input);
					    end = Instant.now();
					    //printNeighbors(map);
					    timeElapsed = Duration.between(start, end);
					    int pairs = 0;
					    for(Particle p : map.keySet())
					        pairs += map.get(p).size();
					    System.out.println("M = " +input.getM() +". CellIndexFinder Execution finished in " + timeElapsed.toMillis() + " ms and found " +pairs/2 +" pairs");
					}
				} catch (Exception e) {
					System.out.println("Max M reached!");
				}
            }
        }
    }
    
    private static void outputNeighbors(Map<Particle, Set<Particle>> map) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE));
        if(map != null){
            for(Particle p : map.keySet())
            {
            	writer.write("[" +p.getId() +"\t");
                for(Particle n : map.get(p))
                	writer.write(" " +n.getId());
                writer.write("].\n");
            }
        }
	    writer.close();
	}

    private static Map<Particle, Set<Particle>> runFinder( NeighborFinder finder, Input input) {
        Map<Particle, Set<Particle>> map = null;
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

    private static void printNeighbors( Map<Particle, Set<Particle>> map ) {
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
