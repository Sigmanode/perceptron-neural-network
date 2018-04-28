import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Render extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	Timer timer = new Timer(10, null);
	NeuralNet brain;

	public Render(NeuralNet brain) {
		this.brain = brain;
		timer.start();
		this.setBackground(new Color(255, 255, 255));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(0, 0, 0));

		for (int i = 0; i < brain.getVecLayers().size(); i++) {
			for (int j = 0; j < brain.getVecLayers().get(i).getNumNeurons(); j++) {
				int numNeurons = brain.getVecLayers().get(i).getNumNeurons();
				g.drawOval((500 / (brain.getVecLayers().size() + 2) * (i + 2)), (500 / (numNeurons + 1)) * (j + 1), 10,
						10);
			}
		}
		double[] inputs = { 18.0, 5.0, -4.0, 130.0 };
		for (int i = 0; i < brain.getNumInputs(); i++) {
			g.drawString(Double.toString(inputs[i]), 500 / (brain.getVecLayers().size() + 2) - 30,
					(500 / (brain.getNumInputs() + 1) * (i + 1)));
		}
		
		for (int j = 0; j < brain.getNumInputs(); j++) {
			
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		Toolkit.getDefaultToolkit().sync();
		repaint();
	}
}
