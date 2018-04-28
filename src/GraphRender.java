import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphRender extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	Timer timer = new Timer(10, this);
	Vector<Double> xCoords, yCoords, xCoords1, yCoords1;

	public GraphRender(Vector<Double> xCoords, Vector<Double> yCoords, Vector<Double> xCoords1, Vector<Double> yCoords1) {
		this.xCoords = xCoords;
		this.yCoords = yCoords;
		this.xCoords1 = xCoords1;
		this.yCoords1 = yCoords1;
		timer.start();
		this.setBackground(new Color(255, 255, 255));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(0, 0, 0));
		Graph.drawLineGraph(g, this.xCoords, this.yCoords, this.xCoords1, this.yCoords1, 0, 0, GenePoolTest.SIM_WIDTH, GenePoolTest.SIM_HEIGHT);
		Graph.drawLineGraph(g, this.xCoords, this.yCoords, this.xCoords1, this.yCoords1, 0, 0, GenePoolTest.SIM_WIDTH, GenePoolTest.SIM_HEIGHT);
	}
	
	public void update(Vector<Double> xCoords, Vector<Double> yCoords, Vector<Double> xCoords1, Vector<Double> yCoords1) {
		this.xCoords = xCoords;
		this.yCoords = yCoords;
		this.xCoords1 = xCoords1;
		this.yCoords1 = yCoords1;
	}
	
	public void actionPerformed(ActionEvent e) {
		Toolkit.getDefaultToolkit().sync();
		repaint();
	}
}
