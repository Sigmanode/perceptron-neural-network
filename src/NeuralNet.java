import java.util.Vector;

public class NeuralNet {
	private final static int BIAS = -1;
	private final static int ACTIVATION_RESPONSE = 1;

	public final static int NUMBER_OF_HIDDEN_LAYERS = 1;
	public final static int NUMBER_OF_NEURONS_PER_HIDDEN_LAYER = 4;

	private int numInputs, numOutputs, numHiddenLayers, neuronsPerHiddenLayer;
	private Vector<NeuronLayer> vecLayers = new Vector<NeuronLayer>();

	public NeuralNet(Vector<Double> weights, int numInputs, int numOutputs) {
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		this.numHiddenLayers = NUMBER_OF_HIDDEN_LAYERS;
		this.neuronsPerHiddenLayer = NUMBER_OF_NEURONS_PER_HIDDEN_LAYER;

		createNet();
		this.setWeight(weights);
	}

	public NeuralNet(int numInputs, int numOutputs) {
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		this.numHiddenLayers = NUMBER_OF_HIDDEN_LAYERS;
		this.neuronsPerHiddenLayer = NUMBER_OF_NEURONS_PER_HIDDEN_LAYER;

		createNet();
	}

	public void createNet() {
		if (numHiddenLayers > 0) {
			vecLayers.add(new NeuronLayer(this.neuronsPerHiddenLayer, this.numInputs));
		}
		for (int i = 1; i < numHiddenLayers; i++) {
			vecLayers.add(new NeuronLayer(this.neuronsPerHiddenLayer, this.neuronsPerHiddenLayer));
		}
		if (numHiddenLayers > 0) {
			vecLayers.add(new NeuronLayer(this.numOutputs, this.neuronsPerHiddenLayer));
		} else {
			vecLayers.add(new NeuronLayer(this.numOutputs, this.numInputs));
		}
	}

	public void setWeight(Vector<Double> weights) {
		int cWeight = 0;

		for (int i = 0; i < numHiddenLayers + 1; i++) {
			for (int j = 0; j < vecLayers.get(i).getVecNeurons().size(); j++) {
				for (int k = 0; k < vecLayers.get(i).getVecNeurons().get(j).getVecWeight().size(); k++) {
					vecLayers.get(i).getVecNeurons().get(j).getVecWeight().set(k, weights.get(cWeight++));
				}
			}
		}
	}

	public void setWeight(int index, double newWeight) {
		Vector<Double> tempWeights = this.getWeights();
		tempWeights.set(index, newWeight);
		this.setWeight(tempWeights);
	}

	public Vector<Double> getWeights() {
		Vector<Double> netWeights = new Vector<Double>();
		for (int i = 0; i < vecLayers.size(); i++) {
			for (int j = 0; j < vecLayers.get(i).getVecNeurons().size(); j++) {
				for (int k = 0; k < vecLayers.get(i).getVecNeurons().get(j).getVecWeight().size(); k++) {
					netWeights.add(vecLayers.get(i).getVecNeurons().get(j).getVecWeight().get(k));
				}
			}
		}
		return netWeights;
	}

	public Vector<Double> update(Vector<Double> inputs) {
		Vector<Double> outputs = new Vector<Double>();
		for (int i = 0; i < numHiddenLayers + 1; i++) {
			if (i > 0) {
				inputs = new Vector<Double>(outputs);
			}

			outputs.clear();

			for (int j = 0; j < vecLayers.get(i).getNumNeurons(); j++) {
				double netinput = 0;

				int totalInputs = vecLayers.get(i).getVecNeurons().get(j).getNumInputs();

				for (int k = 0; k < totalInputs; k++) {
					netinput += vecLayers.get(i).getVecNeurons().get(j).getVecWeight().get(k) * inputs.get(k);
				}

				netinput += vecLayers.get(i).getVecNeurons().get(j).getVecWeight().get(totalInputs) * BIAS;

				outputs.add(sigmoid(netinput, ACTIVATION_RESPONSE));
			}
			// System.out.println("\nOutputs: " + outputs + "\n");
		}
		//System.out.println("\nOutputs: " + outputs + "\n");
		return outputs;
	}

	public double sigmoid(double netinput, double response) {
		return (1 / (1 + Math.exp(-netinput / response)));
	}

	public Vector<NeuronLayer> getVecLayers() {
		return vecLayers;
	}

	public void setVecLayers(Vector<NeuronLayer> vecLayers) {
		this.vecLayers = vecLayers;
	}

	public int getNumInputs() {
		return numInputs;
	}

	public void setNumInputs(int numInputs) {
		this.numInputs = numInputs;
	}

	public int getNumOutputs() {
		return numOutputs;
	}

	public void setNumOutputs(int numOutputs) {
		this.numOutputs = numOutputs;
	}

}
