package ar.edu.itba.ss.cellindexmethod;

import java.time.Duration;
import java.time.Instant;

public class App
{
    public static void main( String[] args )
    {
        Input input = getInput();
        Instant start = Instant.now();
        CellIndexFinder finder = new CellIndexFinder();
        try{
        	finder.findNeighbors(input);
		}
        catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        Instant end = Instant.now();
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
