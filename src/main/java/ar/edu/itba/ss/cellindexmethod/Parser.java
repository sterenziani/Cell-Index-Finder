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
		int N = 7;
		double L = 5;
		int M = 3;
		double rc = 0.45;
		boolean wallPeriod = true;
		// Asumimos que está parseado el archivo. Si hay algo mal, tirar excepción	
		
		List<Particle> list = new LinkedList<Particle>();
		
		
		list.add(new Particle(1, 1.5, 1.5, 0.25));
		list.add(new Particle(2, 2.1, 1.5, 0.25));
		list.add(new Particle(3, 3.5, 1.05, 0.25));
		list.add(new Particle(4, 2.1, 2.1, 0.25));
		list.add(new Particle(5, 0.95, 0.95, 0.25));
		list.add(new Particle(6, 2.01, 0.95, 0.25));
		list.add(new Particle(7, 4.01, 2.05, 0.25));
		list.add(new Particle(8, 4.6, 4.5, 0.25));
		list.add(new Particle(9, 4.6, 0.4, 0.25));
		
		/*
		// OVERLAPPING PARTICLES:
		list.add(new Particle(11, 1.5, 1.5, 0.25));
		list.add(new Particle(12, 1.5, 1.6, 0.25));
		list.add(new Particle(13, 2.5, 1.95, 0.25));
		list.add(new Particle(14, 2.5, 2.05, 0.25));
		*/
		return new Input(N, L, M, rc, wallPeriod, list);
	}
}
