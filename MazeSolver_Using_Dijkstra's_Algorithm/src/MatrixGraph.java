import java.util.Iterator;

public class MatrixGraph extends AbstractGraph {
	private double[][] edges;
	
	public MatrixGraph(int numV, boolean directed) {
		super(numV, directed);	
		edges = new double[numV][numV];
		for (int i = 0; i < numV; i++) {
			for (int j = 0; j < numV; j++) {
				edges[i][j] = Double.POSITIVE_INFINITY;
			}
		}
	}

	@Override
	public void insert(Edge edge) {
		if (edge.getSource()< 0 || edge.getSource() > getNumV()-1 || edge.getDest()< 0 || edge.getDest() > getNumV()-1) {
			return;
		}
		edges[edge.getSource()][edge.getDest()] = edge.getWeight();
		if(!isDirected()) {
			edges[edge.getDest()][edge.getSource()] = edge.getWeight();
		}
	}

	@Override
	public boolean isEdge(int source, int dest) {
		return edges[source][dest] < Double.POSITIVE_INFINITY;
	}

	@Override
	public Edge getEdge(int source, int dest) {
		if(isEdge(source, dest)) return new Edge(source, dest, edges[source][dest]);
		return null;
	}

	@Override
	public Iterator<Edge> edgeIterator(int source) {
		return null;
	}

}
