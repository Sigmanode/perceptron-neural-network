import java.util.Vector;

import javax.swing.JFrame;

public class GenePoolTest {
	public final static int SIM_WIDTH = 800;
	public final static int SIM_HEIGHT = 600;

	public static void main(String args[]) {
		GenePool genePool = new GenePool();
		Vector<Double> xCoords = new Vector<Double>();
		Vector<Double> yCoords = new Vector<Double>();
		Vector<Double> xCoords1 = new Vector<Double>();
		Vector<Double> yCoords1 = new Vector<Double>();

		JFrame frame = new JFrame();
		frame.setSize(SIM_WIDTH + 7, SIM_HEIGHT + 29);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		GraphRender graph = new GraphRender(xCoords, yCoords, xCoords1, yCoords1);
		frame.add(graph);
		frame.setVisible(true);

		for (int i = 0; i < 0; i++) {
			//System.out.println("Generation " + i + ": ");
			genePool.quickUpdate(10000, false);
			xCoords.add((double) i);
			yCoords.add(genePool.getAverageFitness());
			xCoords1.add((double) i);
			yCoords1.add(genePool.getBestFitness());
			graph.update(xCoords, yCoords, xCoords1, yCoords1);
			genePool.update();

		}
		frame.remove(graph);
		GenePoolTestRender panel = new GenePoolTestRender(genePool);
		frame.setSize(SIM_WIDTH + 400 + 7, SIM_HEIGHT + 29);
		frame.add(panel);
		frame.setVisible(false);
		frame.setVisible(true);

	}
}
