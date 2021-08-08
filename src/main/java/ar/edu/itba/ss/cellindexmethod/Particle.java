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
	
	public double getEdgeDistanceTo(Particle p2, double L, int M, boolean loop)
	{
		if(loop)
			return getLoopingEdgeDistanceTo(p2, L, M);
		double centerDistance = Math.sqrt(Math.pow(p2.getX() - x, 2) + Math.pow(p2.getY() - y, 2));
		return centerDistance - radius - p2.getRadius();
	}
	
	public double getLoopingEdgeDistanceTo(Particle p2, double L, int M)
	{
		double x1 = x;
		double y1 = y;
		double x2 = p2.getX();
		double y2 = p2.getY();
		if(x1 < x2 && Math.abs(x1-x2) > Math.abs(L+x1-x2))
			x1 += L;
		else if(x2 < x1 && Math.abs(x1-x2) > Math.abs(x1-x2-L))
			x2 += L;
		if(y1 < y2 && Math.abs(y1-y2) > Math.abs(L+y1-y2))
			y1 += L;
		else if(y2 < y1 && Math.abs(y1-y2) > Math.abs(y1-y2-L))
			y2 += L;
		double centerDistance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return centerDistance - radius - p2.getRadius();
	}
	
	public boolean isNeighbor(Particle p2, double L, int M, boolean loop, double rc)
	{
		return Double.compare(getEdgeDistanceTo(p2, L, M, loop), rc) <= 0;
	}
	
	public boolean isOverlapping(Particle p, double L, int M, boolean loop)
	{
		return getEdgeDistanceTo(p, L, M, loop) < 0;
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
		if (id != other.getId())
			return false;
		return true;
	}
}
