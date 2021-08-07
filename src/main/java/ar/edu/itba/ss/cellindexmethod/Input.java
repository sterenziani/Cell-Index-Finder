package ar.edu.itba.ss.cellindexmethod;
import java.util.List;

public class Input {
	final private long N;
	final private double L;
	final private int M;
	final private double rc;
	final boolean wallPeriod;
	final private List<Particle> particles;
	
	public Input(long N, double L, int M, double rc, boolean wallPeriod, List<Particle> particles)
	{
		this.N = N;
		this.L = L;
		this.M = M;
		this.rc = rc;
		this.wallPeriod = wallPeriod;
		this.particles = particles;
	}

	public long getN() {
		return N;
	}

	public double getL() {
		return L;
	}

	public int getM() {
		return M;
	}

	public double getRc() {
		return rc;
	}

	public List<Particle> getParticles() {
		return particles;
	}
}
