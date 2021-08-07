package ar.edu.itba.ss.cellindexmethod;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Cell {
	private List<CIMParticle> particles;
	
	public Cell()
	{
		this.particles = new LinkedList<>();
	}

	public boolean addParticle(CIMParticle p) {
		return particles.add(p);
	}
	
	public boolean addParticle(Particle p) {
		CIMParticle cim = new CIMParticle(p);
		return particles.add(cim);
	}
	
	public boolean removeParticle(CIMParticle p) {
		return particles.remove(p);
	}
	
	public void removeParticle(Particle p) {
		particles = particles.stream().filter(e -> !e.getParticle().equals(p)).collect(Collectors.toList());
	}
	
	public List<CIMParticle> getList(){
		return particles;
	}
	
	public CIMParticle getHead() {
		if(particles.isEmpty())
			return null;
		return particles.get(0);
	}
}
