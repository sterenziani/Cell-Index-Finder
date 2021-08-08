package ar.edu.itba.ss.cellindexmethod;

import java.time.Duration;
import java.time.Instant;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Input input = getInput();

        Instant start = Instant.now();
        // time passes
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(timeElapsed.toHours() + ":" + timeElapsed.toMinutes() + ":" + timeElapsed.getSeconds());
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
