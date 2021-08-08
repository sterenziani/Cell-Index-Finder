package ar.edu.itba.ss.cellindexmethod;

public class Particle {
	private long id;
	private double x;
	private double y;
	private double radius;
	
	public Particle(long id, double x, double y, double radius)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public boolean isNeighbor(Particle p2, double rc)
	{
		double centerDistance = Math.sqrt(Math.pow(p2.getX() - x, 2) + Math.pow(p2.getY() - y, 2));
		double edgeDistance = centerDistance - radius - p2.getRadius();
		return edgeDistance <= rc;
	}
	
	public long getId() {
		return id;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getRadius() {
		return radius;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Particle other = (Particle) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
