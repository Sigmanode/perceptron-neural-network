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

public class GenePoolTestRender extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	Timer timer = new Timer(10, this);
	GenePool genePool;
	int x = 0;

	public GenePoolTestRender(GenePool genePool) {
		this.genePool = genePool;
		timer.start();
		this.setBackground(new Color(255, 255, 255));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(0, 0, 0));
		g.drawRect(0, 0, GenePoolTest.SIM_WIDTH, GenePoolTest.SIM_HEIGHT);
		for (int i = 0; i < genePool.getVecOrganism().size(); i++) {

			double sweeperX = ((Minesweeper) genePool.getVecOrganism().get(i)).getX();
			double sweeperY = ((Minesweeper) genePool.getVecOrganism().get(i)).getY();
			double sweeperRadius = ((Minesweeper) genePool.getVecOrganism().get(i)).getRadius();
			double sweeperRotation = ((Minesweeper) genePool.getVecOrganism().get(i)).getRotation();

			g.setColor(new Color(255, 255, 255));
			g.fillOval((int) ((((Minesweeper) genePool.getVecOrganism().get(i)).getX()) - (sweeperRadius / 2)),
					(int) ((((Minesweeper) genePool.getVecOrganism().get(i)).getY()) - (sweeperRadius / 2)),
					(int) sweeperRadius, (int) sweeperRadius);
			g.setColor(new Color(0, 0, 0));
			g.drawOval((int) ((((Minesweeper) genePool.getVecOrganism().get(i)).getX()) - (sweeperRadius / 2)),
					(int) ((((Minesweeper) genePool.getVecOrganism().get(i)).getY()) - (sweeperRadius / 2)),
					(int) sweeperRadius, (int) sweeperRadius);
			g.drawLine((int) sweeperX, (int) sweeperY,
					(int) (sweeperX + (sweeperRadius / 2) * Math.sin(sweeperRotation)),
					(int) (sweeperY - (sweeperRadius / 2) * Math.cos(sweeperRotation)));
		}

		for (int i = 0; i < genePool.vecFood.size(); i++) {
			g.setColor(new Color(40, 180, 40));
			g.fillOval(genePool.vecFood.get(i).x - (Food.RADIUS / 2), genePool.vecFood.get(i).y - (Food.RADIUS / 2),
					Food.RADIUS, Food.RADIUS);
		}
		
		g.setColor(new Color(255, 0, 0));
		/*for (int i = 0; i < genePool.getVecOrganism().size(); i++) {
			Vector<Double> sweeperInputs = genePool.getVecOrganism().get(i).getRawInput();
			double sweeperX = ((Minesweeper) genePool.getVecOrganism().get(i)).getX();
			double sweeperY = ((Minesweeper) genePool.getVecOrganism().get(i)).getY();
			double sweeperRotation = ((Minesweeper) genePool.getVecOrganism().get(i)).getRotation();
			g.drawLine((int) sweeperX, (int) sweeperY,
					(int) (sweeperX + (sweeperInputs.get(0) * Math.sin(sweeperRotation + sweeperInputs.get(1)))),
					(int) (sweeperY - (sweeperInputs.get(0) * Math.cos(sweeperRotation + sweeperInputs.get(1)))));
			//g.drawString(Double.toString(sweeperInputs.get(1)), (int) sweeperX + 20, (int) sweeperY);
		}*/
		
		Graph.drawLineGraph(g, genePool.xCoords, genePool.yCoords, genePool.xCoords1, genePool.yCoords1, GenePoolTest.SIM_WIDTH, 0, 400,GenePoolTest.SIM_HEIGHT);
		
	}

	public void actionPerformed(ActionEvent e) {
		Vector<Double> inputs = new Vector<Double>();
		for (int i = 0; i < genePool.getVecOrganism().size(); i++) {

			double lowestDistance = Math.pow(2, 64);
			double lowestAngle = Math.pow(2, 64);
			double lowestFoodIndex = 0;
			for (int j = 0; j < genePool.vecFood.size(); j++) {
				if (VectorUtil.distance(((Minesweeper) genePool.getVecOrganism().get(i)).getX(),
						((Minesweeper) genePool.getVecOrganism().get(i)).getY(), (double) genePool.vecFood.get(j).x,
						(double) genePool.vecFood.get(j).y) < lowestDistance) {
					lowestDistance = VectorUtil.distance(((Minesweeper) genePool.getVecOrganism().get(i)).getX(),
							((Minesweeper) genePool.getVecOrganism().get(i)).getY(),
							(double) genePool.vecFood.get(j).x, (double) genePool.vecFood.get(j).y);
					lowestAngle = VectorUtil.angle(((Minesweeper) genePool.getVecOrganism().get(i)).getX(),
							((Minesweeper) genePool.getVecOrganism().get(i)).getY(),
							(double) genePool.vecFood.get(j).x, (double) genePool.vecFood.get(j).y);
					lowestFoodIndex = j;
				}

			}

			double rotationInput = Math.toRadians(lowestAngle) - ((Minesweeper) genePool.getVecOrganism().get(i)).rotation;
			if (rotationInput < 0) {
				if (VectorUtil.modulus(rotationInput) > Math.PI) {
					rotationInput = (Math.PI * 2 - VectorUtil.modulus(rotationInput));
				}
			} else {
				if (VectorUtil.modulus(rotationInput) > Math.PI) {
					rotationInput = -(Math.PI * 2 - VectorUtil.modulus(rotationInput));
				}
			}

			inputs.clear();
			inputs.add(lowestDistance / VectorUtil.distance(0, 0, 40, 40));
			inputs.add((rotationInput) / (Math.PI * 2));
			Vector<Double> rawInputs = new Vector<Double>();
			rawInputs.add(lowestDistance);
			rawInputs.add(rotationInput);
			genePool.getVecOrganism().get(i).setRawInput(rawInputs);
			// System.out.println(inputs);
			((Minesweeper) genePool.getVecOrganism().get(i)).update(inputs);
		}

		for (int i = 0; i < genePool.vecFood.size(); i++) {
			for (int j = 0; j < genePool.getVecOrganism().size(); j++) {
				if (genePool.vecFood.get(i).collision(((Minesweeper) genePool.getVecOrganism().get(j)).getX(),
						((Minesweeper) genePool.getVecOrganism().get(j)).getY(), 6)) {
					genePool.getVecOrganism().get(j).addFitness(1);
					genePool.vecFood.get(i).reset();
				}
			}
		}
		
		x++;
		 //System.out.println(x++);
		if (x >= 500) {
			x = 0;
			genePool.quickUpdate(19500);
		}

		Toolkit.getDefaultToolkit().sync();
		repaint();
	}
}
