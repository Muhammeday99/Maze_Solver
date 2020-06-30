import java.io.File;
import java.io.FileNotFoundException;

public class main {

	public static void main(String[] args) throws FileNotFoundException {
		File mazefile = new File("maze.txt");
		mazeSolver solver = new mazeSolver();
		solver.generateMaze(mazefile);
		solver.getEdges();
		solver.DrawMaze();
		solver.search();

	}

}
