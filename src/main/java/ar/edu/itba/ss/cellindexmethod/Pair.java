package ar.edu.itba.ss.cellindexmethod;

public class Pair {
    Particle p1, p2;

    public Pair(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Particle getP1() {
        return p1;
    }

    public Particle getP2() {
        return p2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*p1.hashCode() + p2.hashCode();
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
        Pair other = (Pair) obj;
        return p1.equals(other.p1) && p2.equals(other.p2);
    }
}
