import java.util.Random;
import java.util.Vector;

public class Organism {
	private final static double MUTATION_RATE = 0.1;
	private final static double MAX_MUTATION = 0.3;
	public Vector<Double> input = new Vector<Double>(), output = new Vector<Double>(), rawInput = new Vector<Double>();
	public NeuralNet brain;
	private double fitness;
	
	public Organism(int numInputs, int numOutputs) {
		this.brain = new NeuralNet(numInputs, numOutputs);
		this.fitness = 0;
	}
	
	public Organism(NeuralNet brain) {
		this.brain = brain;
		this.fitness = 0;
	}

	public Organism(NeuralNet brain, double fitness) {
		this.brain = brain;
		this.fitness = fitness;
	}

	public void mutate() {
		for (int i = 0; i < this.brain.getWeights().size(); i++) {
			if (new Random().nextDouble() < MUTATION_RATE) {
				this.brain.setWeight(i,
						this.brain.getWeights().get(i) + ((new Random().nextDouble() - 0.5) * MAX_MUTATION));
			}
		}
	}

	public void display() {
		int vectorPosition = 0;
		for (int i = 0; i < NeuralNet.NUMBER_OF_NEURONS_PER_HIDDEN_LAYER; i++) {
			for (int j = 0; j < this.brain.getNumInputs(); j++) {
				System.out.print("|" + this.brain.getWeights().get(vectorPosition++));
			}
			System.out.print("|\n");
		}

		System.out.println();

		for (int i = 1; i < NeuralNet.NUMBER_OF_HIDDEN_LAYERS; i++) {
			for (int j = 0; j < NeuralNet.NUMBER_OF_NEURONS_PER_HIDDEN_LAYER; j++) {
				for (int k = 0; k < NeuralNet.NUMBER_OF_NEURONS_PER_HIDDEN_LAYER; k++) {
					System.out.print("|" + this.brain.getWeights().get(vectorPosition++));
				}
				System.out.print("|\n");
			}
			System.out.println();
		}

		for (int i = 0; i < this.brain.getNumOutputs(); i++) {
			for (int j = 0; j < NeuralNet.NUMBER_OF_NEURONS_PER_HIDDEN_LAYER; j++) {
				System.out.print("|" + this.brain.getWeights().get(vectorPosition++));
			}
			System.out.print("|\n");
		}
		System.out.println();
	}

	public NeuralNet getBrain() {
		return brain;
	}

	public void setBrain(NeuralNet brain) {
		this.brain = brain;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public void addFitness(double fitness){
		this.fitness += fitness;
	}

	public Vector<Double> getInput() {
		return input;
	}

	public void setInput(Vector<Double> input) {
		this.input = input;
	}

	public void addInput(double input) {
		this.input.add(input);
	}

	public Vector<Double> getOutput() {
		return output;
	}

	public void setOutput(Vector<Double> output) {
		this.output = output;
	}

	public Vector<Double> getRawInput() {
		return rawInput;
	}

	public void setRawInput(Vector<Double> rawInput) {
		this.rawInput = rawInput;
	}
	

}
