import java.util.Random;
import java.util.Vector;

public class Neuron {
	private Vector<Double> vecWeight = new Vector<Double>();

	public Neuron(int numInputs) {
		for (int i = 0; i < numInputs + 1; i++) {
			vecWeight.add((new Random().nextDouble() * 2) - 1);
		}
	}

	public int getNumInputs() {
		return vecWeight.size() - 1;
	}

	public Vector<Double> getVecWeight() {
		return vecWeight;
	}

	public void setVecWeight(int index, double weight) {
		this.vecWeight.set(index, weight);
	}

	public void setVecWeight(Vector<Double> vecWeight) {
		this.vecWeight = vecWeight;
	}

}
