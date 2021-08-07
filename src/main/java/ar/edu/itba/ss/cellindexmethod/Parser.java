package ar.edu.itba.ss.cellindexmethod;

import java.util.LinkedList;
import java.util.List;

public class Parser{
	private static Parser parser = null;
	private Parser() {}
	
	public static Parser getInstance() {
		if(parser == null)
			parser = new Parser();
		return parser;
	}
	
	public Input parseFiles(String path) throws Exception{
		return parseFileStatic(path);
	}
	
	private Input parseFileStatic(String path) throws Exception{
		// TODO: Parse file
		int N = 3;
		double L = 10;
		int M = 10;
		int rc = 1;
		boolean wallPeriod = true;
		// Asumimos que está parseado el archivo. Si hay algo mal, tirar excepción	
		
		List<Particle> list = new LinkedList<Particle>();
		list.add(new Particle(1, 2, 2, 0.5));
		list.add(new Particle(2, 3, 3, 1));
		list.add(new Particle(3, 6, 4, 1));
		return new Input(N, L, M, rc, wallPeriod, list);
	}
}
