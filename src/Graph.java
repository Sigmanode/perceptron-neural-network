import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class Graph {
	private static final int MARGIN = 40;
	private static final int END_MARGIN = 20;

	public static void drawLineGraph(Graphics g, Vector<Double> xCoords, Vector<Double> yCoords, int x, int y,
			int width, int height) {
		g.setColor(new Color(0, 0, 0));
		g.drawLine(x + MARGIN, y, x + MARGIN, y + height);
		g.drawLine(x, y + height - MARGIN, x + width, y + height - MARGIN);
		double largestX = 0;
		double largestY = 0;
		for (int i = 0; i < xCoords.size(); i++) {
			if (xCoords.get(i) > largestX) {
				largestX = xCoords.get(i);
			}
			if (yCoords.get(i) > largestY) {
				largestY = yCoords.get(i);
			}
		}
		g.setColor(new Color(0, 0, 255));
		for (int i = 0; i < xCoords.size() - 1; i++) {
			g.drawLine((int) (MARGIN + ((width - MARGIN) * (xCoords.get(i) / largestX))),
					(int) ((height - MARGIN) - ((height - MARGIN) * (yCoords.get(i) / largestY))),
					(int) (MARGIN + ((width - MARGIN) * (xCoords.get(i + 1) / largestX))),
					(int) ((height - MARGIN) - ((height - MARGIN) * (yCoords.get(i + 1) / largestY))));
		}
	}

	public static void drawLineGraph(Graphics g, Vector<Double> xCoords, Vector<Double> yCoords,
			Vector<Double> xCoords1, Vector<Double> yCoords1, int x, int y, int width, int height) {
		g.setColor(new Color(0, 0, 0));
		g.drawLine(x + MARGIN, y, x + MARGIN, y + height);
		g.drawLine(x, y + height - MARGIN, x + width, y + height - MARGIN);
		g.drawLine(x + MARGIN - 3, y + 5, x + MARGIN, y);
		g.drawLine(x + MARGIN + 3, y + 5, x + MARGIN, y);
		g.drawLine(x + width - 5, y + height - MARGIN - 3, x + width, y + height - MARGIN);
		g.drawLine(x + width - 5, y + height - MARGIN + 3, x + width, y + height - MARGIN);
		double largestX = 0;
		double largestY = 0;
		for (int i = 0; i < xCoords.size(); i++) {
			if (xCoords.get(i) > largestX) {
				largestX = xCoords.get(i);
			}
			if (xCoords1.get(i) > largestX) {
				largestX = xCoords1.get(i);
			}
			if (yCoords.get(i) > largestY) {
				largestY = yCoords.get(i);
			}
			if (yCoords1.get(i) > largestY) {
				largestY = yCoords1.get(i);
			}
		}
		g.setColor(new Color(0, 0, 255));
		for (int i = 0; i < xCoords.size() - 1; i++) {
			g.drawLine(x + (int) (MARGIN + ((width - MARGIN - END_MARGIN) * (xCoords.get(i) / largestX))), y
					+ (int) ((height - MARGIN) - ((height - MARGIN - END_MARGIN) * (yCoords.get(i) / largestY))), x
					+ (int) (MARGIN + ((width - MARGIN - END_MARGIN) * (xCoords.get(i + 1) / largestX))), y
					+ (int) ((height - MARGIN) - ((height - MARGIN - END_MARGIN) * (yCoords.get(i + 1) / largestY))));
		}
		g.setColor(new Color(255, 0, 0));
		for (int i = 0; i < xCoords1.size() - 1; i++) {
			g.drawLine(x + (int) (MARGIN + ((width - MARGIN - END_MARGIN) * (xCoords1.get(i) / largestX))), y
					+ (int) ((height - MARGIN) - ((height - MARGIN - END_MARGIN) * (yCoords1.get(i) / largestY))), x
					+ (int) (MARGIN + ((width - MARGIN - END_MARGIN) * (xCoords1.get(i + 1) / largestX))), y
					+ (int) ((height - MARGIN) - ((height - MARGIN - END_MARGIN) * (yCoords1.get(i + 1) / largestY))));
		}
		g.setColor(new Color(0, 0, 0));

		int numReadings = 6;
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (int i = 0; i < numReadings; i++) {
			g.drawLine(x + MARGIN - 3, y + END_MARGIN + ((i) * (height - MARGIN - END_MARGIN) / (numReadings)), x
					+ MARGIN, y + END_MARGIN + ((i) * (height - MARGIN - END_MARGIN) / (numReadings)));

			g.drawString(formatter.format((largestY / (numReadings)) * (numReadings - i)), x + MARGIN - 36, y
					+ END_MARGIN + 4 + ((i) * (height - MARGIN - END_MARGIN) / (numReadings)));
		}
		for (int i = 0; i < numReadings; i++) {
			g.drawLine(MARGIN + x + ((i + 1) * (width - MARGIN - END_MARGIN) / (numReadings)), y + height - MARGIN,
					MARGIN + x + ((i + 1) * (width - MARGIN - END_MARGIN) / (numReadings)), y + height - MARGIN + 3);

			g.drawString(formatter.format((largestX / (numReadings)) * (i+1)), x + MARGIN - 12
					+ ((i + 1) * (width - MARGIN - END_MARGIN) / (numReadings)), y + height - MARGIN + 20);
		}
		g.drawString("Generation: " + Integer.toString(xCoords.size()), width - 130, 15);
	}
}
