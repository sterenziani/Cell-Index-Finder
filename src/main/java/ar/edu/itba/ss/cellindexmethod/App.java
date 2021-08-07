package ar.edu.itba.ss.cellindexmethod;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Input input = getInput();        
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
