import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class mazeSolver {
	ArrayList<ArrayList<Integer>> maze;
	Graph mazeGraph;
	ArrayList<Integer[]> VCoor;
	private int discoverIndex;
	private int[] discoveryOrder;
	private int finishIndex;
	private int[] finishOrder;
	private int[] parent;
	private boolean[] visited;
	
	public mazeSolver() {
		maze = new ArrayList<ArrayList<Integer>>();
	}
	public void generateMaze(File fileName) throws FileNotFoundException {
		Scanner input = new Scanner(fileName);
		String temp;
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int k = 0; 
		
		while(input.hasNextLine()) {
			temp = input.nextLine();
			maze.add(new ArrayList<Integer>());
			for(int i = 0; i<temp.length();i++) {
				String a = "" + temp.charAt(i);
				maze.get(k).add(Integer.valueOf(a));
			}
			k++;
		}
		
		Integer numV = 0;
		
		VCoor = new ArrayList<Integer[]>();
		int v = 0;
		
		for (int i = 0; i < maze.size(); i++) {
			for (int j = 0; j < maze.get(i).size(); j++) {
				if(i==0 && j==0) {
					VCoor.add(new Integer[2]);
					VCoor.get(v)[0] = i;
					VCoor.get(v)[1] = j;
					v++;
					numV++;
				}
				else if(i == maze.size()-1 && j == maze.get(maze.size()-1).size()-1) {
					VCoor.add(new Integer[2]);
					VCoor.get(v)[0] = i;
					VCoor.get(v)[1] = j;
					v++;
					numV++;
				}
				else if (i == 0 && j!=0 && j != maze.get(maze.size()-1).size()-1) {
					if(maze.get(i).get(j) == 0 && maze.get(i+1).get(j) == 0 && (maze.get(i).get(j+1) == 0 || maze.get(i).get(j-1) == 0)){
						VCoor.add(new Integer[2]);
						VCoor.get(v)[0] = i;
						VCoor.get(v)[1] = j;
						v++;
						numV++;
					}
				}
				else if (i != 0 && i != maze.size()-1 && j==0) {
					if(maze.get(i).get(j) == 0 && (maze.get(i+1).get(j) == 0 || maze.get(i-1).get(j) == 0)
						&& maze.get(i).get(j+1) == 0 ){
						VCoor.add(new Integer[2]);
						VCoor.get(v)[0] = i;
						VCoor.get(v)[1] = j;
						v++;
						numV++;
					}
				}
				else if (i == maze.size()-1 && j!=0 && j != maze.get(maze.size()-1).size()-1) {
					if(maze.get(i).get(j) == 0 && maze.get(i-1).get(j) == 0 && (maze.get(i).get(j+1) == 0 || maze.get(i).get(j-1) == 0)) {
						VCoor.add(new Integer[2]);
						VCoor.get(v)[0] = i;
						VCoor.get(v)[1] = j;
						v++;
						numV++;
					}
				}
				else if (j == maze.get(maze.size()-1).size()-1 && i != 0 && i != maze.size()-1) {
					if(maze.get(i).get(j) == 0 && (maze.get(i+1).get(j) == 0 || maze.get(i-1).get(j) == 0) && maze.get(i).get(j-1) == 0) {
						VCoor.add(new Integer[2]);
						VCoor.get(v)[0] = i;
						VCoor.get(v)[1] = j;
						v++;
						numV++;
					}
				}
				else if(i!= 0 && j!=0 && i != maze.size()-1 && j != maze.get(maze.size()-1).size()-1) {
					if(maze.get(i).get(j) == 0 && (maze.get(i+1).get(j) == 0 || maze.get(i-1).get(j) == 0)
							&& (maze.get(i).get(j+1) == 0 || maze.get(i).get(j-1) == 0)) {
							VCoor.add(new Integer[2]);
							VCoor.get(v)[0] = i;
							VCoor.get(v)[1] = j;
							v++;
							numV++;
						}
					}
			}
		}
		
		mazeGraph = AbstractGraph.createGraph(new Scanner(numV.toString()), false, "list");
		
		
		for(int i=0;i<numV;i++) {
			Integer[] vertex = VCoor.get(i);
			int weight = 0;
			int y = vertex[0];
			int x = vertex[1];
			int newx = x;
			int newy = y;
			if(newx >= 0) {
				while(newx < maze.get(y).size() && maze.get(y).get(newx) != 1) {
					if(isVertex(y, newx) && newx != x) {
						int dest = indexOfVertex(y, newx);
						mazeGraph.insert(new Edge(VCoor.indexOf(vertex), dest,weight));
						weight = 0;
						break;
					}else {
						newx++;
						weight++;
					}
				}
			}
			if(newy>=0) {
				while(newy < maze.size() && maze.get(newy).get(x) != 1) {
					if(isVertex(newy, x) && newy != y) {
						int dest = indexOfVertex(newy, x);
						mazeGraph.insert(new Edge(VCoor.indexOf(vertex), dest,weight));
						weight = 0;
						break;
					}else {
						newy++;
						weight++;
					}
				}
			}
		}
		
		
		
	}
	
	public void getEdges() {
		int numV = mazeGraph.getNumV();
		for(int i=0;i<numV;i++) {
			System.out.println("edges of vertex "+ VCoor.get(i)[0] + "," + VCoor.get(i)[1] + ":");
			for(int j=0;j<numV;j++) {
				if(mazeGraph.isEdge(i, j)) {
					System.out.println(VCoor.get(j)[0] + "," + VCoor.get(j)[1] + " weight: " + mazeGraph.getEdge(i, j).getWeight());
				}
			}
		}
	}
	
	public void DrawMaze() {
		for (int i = 0; i < maze.size(); i++) {
			for (int j = 0; j < maze.get(i).size(); j++) {
				if(maze.get(i).get(j) == 1) {
					System.out.print("|X|");
				}else if (maze.get(i).get(j) == 0) {
					if(isVertex(i, j)){
						int m = indexOfVertex(i, j);
						if(m<10)
							System.out.print(" "+m+" ");
						else System.out.print(" "+m);
					}else System.out.print("   ");
				}
			}
			System.out.println();
		}
	}
	
	private boolean isVertex(int y, int x) {
		for (int i = 0; i < VCoor.size(); i++) {
			if(VCoor.get(i)[0] == y && VCoor.get(i)[1] == x) return true;
		}
		return false;
	}
	
	private int indexOfVertex(int y, int x) {
		for (int i = 0; i < VCoor.size(); i++) {
			if(VCoor.get(i)[0] == y && VCoor.get(i)[1] == x) return i;
		}
		return -1;
	}
	
	public void search() {
		int target = mazeGraph.getNumV()-1;
		int[] pred = djkistra();
		List<Integer> shortest = new LinkedList<Integer>();
		shortest.add(target);
		while(target != 0) {
			shortest.add(pred[target]);
			target = pred[target];
		}
		for (int i = shortest.size()-1; i >= 0; i--) {
			System.out.print("->"+shortest.get(i));
		}
	}
	
	private boolean DFS(int current, int target) {
		/* Mark the current vertex visited. */
		visited[current] = true;
		discoveryOrder[discoverIndex++] = current;
		if(current == target) return true;
		/* Examine each vertex adjacent to the current vertex */
		Iterator<Edge> itr = mazeGraph.edgeIterator(current);
		while (itr.hasNext()) {
			int neighbor = itr.next().getDest();
			/* Process a neighbor that has not been visited */
			if (!visited[neighbor]) {
				/* Insert (current, neighbor) into the depth first search tree. */
				parent[neighbor] = current;
				/* Recursively apply the algorithm starting at neighbor. */
				return DFS(neighbor, target);
			}
		}
		/* Mark current finished. */
		finishOrder[finishIndex++] = current;
		return false;
	}
	
	public int[] djkistra() {
		List<Integer> visited = new LinkedList<Integer>();
		HashSet<Integer> v_s = new HashSet<Integer>();
		int[] pred = new int[mazeGraph.getNumV()];
		double[] dist = new double[mazeGraph.getNumV()];
		int start = 0;
		for(int i=1;i<mazeGraph.getNumV();i++) {
			v_s.add(i);
		}
		
		visited.add(start);
		
		for(int v : v_s) {
			pred[v] = start;
			if(mazeGraph.isEdge(start, v))
			dist[v] = mazeGraph.getEdge(start, v).getWeight();
			else dist[v] = Double.POSITIVE_INFINITY;
		}
		
		while(v_s.size() != 0) {
			double min = Double.POSITIVE_INFINITY;
			int u = -1;
			for(int v : v_s) {
				if(dist[v]<min) {
					min = dist[v];
					u = v;
				}
			}
			
			for(int v : v_s) {
				if(mazeGraph.isEdge(u, v)) {
					if(dist[u] + mazeGraph.getEdge(u, v).getWeight() < dist[v]) {
						dist[v] = dist[u] + mazeGraph.getEdge(u, v).getWeight();
						pred[v] = u;
					}
				}
			}
			v_s.remove(u);
			visited.add(u);
		}
		
		
		
		return pred;
		
	}
}
