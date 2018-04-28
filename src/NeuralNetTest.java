import java.util.Vector;

import javax.swing.JFrame;

public class NeuralNetTest {
	public static void main(String args[]) {
		NeuralNet brain = new NeuralNet(4, 2);
		Vector<Double> inputs = new Vector<Double>();
		Vector<Double> outputs = new Vector<Double>();
		inputs.add(18.0);
		inputs.add(5.0);
		inputs.add(-4.0);
		inputs.add(130.0);
		outputs = brain.update(inputs);
		System.out.println(outputs);

		JFrame frame = new JFrame("Neural Network");
		Render panel = new Render(brain);
		frame.setSize(510, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(panel);
		frame.setVisible(true);

	}
}
