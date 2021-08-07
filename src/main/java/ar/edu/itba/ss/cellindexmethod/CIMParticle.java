package ar.edu.itba.ss.cellindexmethod;

import java.util.HashSet;
import java.util.Set;

public class CIMParticle {
	private Particle particle;
	private Set<Particle> neighbors;
	
	public CIMParticle(Particle p)
	{
		this.particle = p;
		this.neighbors = new HashSet<>();
	}
	
	public Particle getParticle() {
		return particle;
	}
	public Set<Particle> getNeighbors() {
		return neighbors;
	}
}
