import java.util.Random;

public class Food {
	final static int RADIUS = 6;
	int x, y;

	public Food() {
		this.x = (int) (new Random().nextDouble() * GenePoolTest.SIM_WIDTH);
		this.y = (int) (new Random().nextDouble() * GenePoolTest.SIM_HEIGHT);
	}
	
	public void reset(){
		this.x = (int) (new Random().nextDouble() * GenePoolTest.SIM_WIDTH);
		this.y = (int) (new Random().nextDouble() * GenePoolTest.SIM_HEIGHT);
	}
	
	public boolean collision(double testX, double testY, double testRadius) {
		if (Math.sqrt(Math.pow(testX - this.x, 2) + Math.pow(testY - this.y, 2)) < testRadius + RADIUS) {
			return true;
		} else {
			return false;
		}
	}
}
