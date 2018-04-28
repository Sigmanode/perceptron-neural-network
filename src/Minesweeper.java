import java.util.Random;
import java.util.Vector;

public class Minesweeper extends Organism {
	private final static double SPEED = 1.5;
	private final static double ROTATION_SPEED = Math.PI;
	public final static int NUMBER_OF_INPUTS = 2;
	public final static int NUMBER_OF_OUTPUTS = 2;

	double x, y, rotation, radius = 6;

	public Minesweeper() {
		super(NUMBER_OF_INPUTS, NUMBER_OF_OUTPUTS);
		this.initialise(new Random().nextDouble() * GenePoolTest.SIM_WIDTH, new Random().nextDouble() * GenePoolTest.SIM_HEIGHT, new Random().nextDouble()
				* Math.PI * 2);
	}

	public Minesweeper(NeuralNet brain) {
		super(brain);
		this.initialise(new Random().nextDouble() * GenePoolTest.SIM_WIDTH, new Random().nextDouble() * GenePoolTest.SIM_HEIGHT, new Random().nextDouble()
				* Math.PI * 2);
	}

	public Minesweeper(NeuralNet brain, double fitness) {
		super(brain, fitness);
		this.initialise(new Random().nextDouble() * GenePoolTest.SIM_WIDTH, new Random().nextDouble() * GenePoolTest.SIM_HEIGHT, new Random().nextDouble()
				* Math.PI * 2);
	}

	public void initialise(double x, double y, double rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}

	// Move forwards/backwards
	public void update(Vector<Double> input) {
		this.input = input;
		this.output = this.brain.update(this.input);
		/*
		 * double output1 = this.output.get(0) - 0.5; double output2 =
		 * this.output.get(1) - 0.5; this.x += output1 * SPEED *
		 * Math.cos(rotation); this.y += output1 * SPEED * Math.sin(rotation);
		 * System.out.println(output2); this.rotation += output2 *
		 * ROTATION_SPEED;
		 */

		// assign the outputs to the sweepers left & right tracks
		double leftTrack = output.get(0);
		double rightTrack = output.get(1);

		// calculate steering forces
		double rotForce = leftTrack - rightTrack;

		// clamp rotation
		if (rotForce > ROTATION_SPEED) {
			rotForce = ROTATION_SPEED;
		}
		if (rotForce < -ROTATION_SPEED) {
			rotForce = -ROTATION_SPEED;
		}

		rotation += rotForce;
		
		if(rotation < 0){
			rotation += Math.PI*2;
		}
		
		rotation %= Math.PI*2;
		
		

		// update Look At
		this.x += SPEED * Math.sin(rotation);
		this.y -= SPEED * Math.cos(rotation);

		if (this.x < 0) {
			this.x = GenePoolTest.SIM_WIDTH;
		}
		if (this.x > GenePoolTest.SIM_WIDTH) {
			this.x = 0;
		}
		if (this.y > GenePoolTest.SIM_HEIGHT) {
			this.y = 0;
		}
		if (this.y < 0) {
			this.y = GenePoolTest.SIM_HEIGHT;
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	

}
