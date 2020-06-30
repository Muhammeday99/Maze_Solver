
public class Edge {
	private int source;
	private int dest;
	private double weight;
	
	public int getDest() {
		return dest;
	}

	public int getSource() {
		return source;
	}

	public double getWeight() {
		return weight;
	}	
	
	public Edge(int source, int dest) {
		this.dest = dest;
		this.source = source;
		this.weight = 1.0;
	}
	
	public Edge(int source, int dest, double w) {
		this(source, dest);
		weight = w;
	}
	
	@Override
	public boolean equals(Object o) {
		return ((Edge) o).getDest() == dest && ((Edge) o).getSource() == source;
	}
	
	@Override
	public int hashCode() {
		return ((Integer) source).hashCode() + ((Integer) dest).hashCode();
	}

	@Override
	public String toString() {
		return source + "->" + dest;
	}
	
	
	
	
}
