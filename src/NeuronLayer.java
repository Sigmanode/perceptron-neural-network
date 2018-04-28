import java.util.Vector;

public class NeuronLayer {
	Vector<Neuron> vecNeurons = new Vector<Neuron>();

	public NeuronLayer(int numNeurons, int numInputsPerNeuron) {
		for (int i = 0; i < numNeurons; i++) {
			vecNeurons.add(new Neuron(numInputsPerNeuron));
		}
	}

	public int getNumNeurons() {
		return vecNeurons.size();
	}

	public Vector<Neuron> getVecNeurons() {
		return vecNeurons;
	}

	public void setVecNeurons(Vector<Neuron> vecNeurons) {
		this.vecNeurons = vecNeurons;
	}

}
