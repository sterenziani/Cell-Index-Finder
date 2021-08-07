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
}
