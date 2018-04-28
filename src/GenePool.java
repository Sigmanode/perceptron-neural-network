import java.util.Random;
import java.util.Vector;

public class GenePool {
	private final static int POPULATION_SIZE = 20;
	private final static double CROSSOVER_RATE = 0;
	Vector<Organism> vecOrganism = new Vector<Organism>();
	Vector<Food> vecFood = new Vector<Food>();

	// TEMP
	Vector<Double> xCoords = new Vector<Double>();
	Vector<Double> yCoords = new Vector<Double>();
	Vector<Double> xCoords1 = new Vector<Double>();
	Vector<Double> yCoords1 = new Vector<Double>();

	public GenePool(Vector<Double> xCoords, Vector<Double> yCoords, Vector<Double> xCoords1, Vector<Double> yCoords1) {
		this.xCoords = xCoords;
		this.yCoords = yCoords;
		this.xCoords1 = xCoords1;
		this.yCoords1 = yCoords1;
		
		for (int i = 0; i < 10; i++) {
			vecFood.add(new Food());
		}
		for (int i = 0; i < POPULATION_SIZE; i++) {
			this.vecOrganism.add(new Minesweeper());
			((Minesweeper) this.vecOrganism.get(i)).initialise(new Random().nextDouble() * 500,
					new Random().nextDouble() * 500, new Random().nextDouble() * Math.PI * 2);
		}
	}

	public GenePool() {
		for (int i = 0; i < 10; i++) {
			vecFood.add(new Food());
		}
		for (int i = 0; i < POPULATION_SIZE; i++) {
			this.vecOrganism.add(new Minesweeper());
			((Minesweeper) this.vecOrganism.get(i)).initialise(new Random().nextDouble() * 500,
					new Random().nextDouble() * 500, new Random().nextDouble() * Math.PI * 2);
		}
	}

	public void display() {
		System.out.println("Avg. fitness:\t" + this.getAverageFitness());
		System.out.println("Best fitness:\t" + this.getBestFitness());
	}

	public void quickUpdate(int time) {
		this.quickUpdate(time, true);
	}

	public void quickUpdate(int time, boolean fullUpdate) {
		int x = 0;
		Vector<Double> inputs = new Vector<Double>();
		while (x < time) {

			for (int i = 0; i < this.getVecOrganism().size(); i++) {
				double lowestDistance = Math.pow(2, 64);
				double lowestAngle = Math.pow(2, 64);
				double lowestFoodIndex = 0;
				for (int j = 0; j < this.vecFood.size(); j++) {
					if (VectorUtil.distance(((Minesweeper) this.getVecOrganism().get(i)).getX(), ((Minesweeper) this
							.getVecOrganism().get(i)).getY(), (double) this.vecFood.get(j).x, (double) this.vecFood
							.get(j).y) < lowestDistance) {
						lowestDistance = VectorUtil.distance(((Minesweeper) this.getVecOrganism().get(i)).getX(),
								((Minesweeper) this.getVecOrganism().get(i)).getY(), (double) this.vecFood.get(j).x,
								(double) this.vecFood.get(j).y);
						lowestAngle = VectorUtil.angle(((Minesweeper) this.getVecOrganism().get(i)).getX(),
								((Minesweeper) this.getVecOrganism().get(i)).getY(), (double) this.vecFood.get(j).x,
								(double) this.vecFood.get(j).y);
						lowestFoodIndex = j;
					}

				}
				double rotationInput = Math.toRadians(lowestAngle)
						- ((Minesweeper) this.getVecOrganism().get(i)).rotation;
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
				this.getVecOrganism().get(i).setRawInput(rawInputs);

				// System.out.println(i);
				// System.out.println(inputs);
				((Minesweeper) this.getVecOrganism().get(i)).update(inputs);
			}

			for (int i = 0; i < this.vecFood.size(); i++) {
				for (int j = 0; j < this.getVecOrganism().size(); j++) {
					if (this.vecFood.get(i).collision(((Minesweeper) this.getVecOrganism().get(j)).getX(),
							((Minesweeper) this.getVecOrganism().get(j)).getY(), 6)) {
						this.getVecOrganism().get(j).addFitness(1);
						this.vecFood.get(i).reset();
					}
				}
			}
			x++;
		}
		if (fullUpdate) {
			this.update();
		}
	}

	public void update() {
		xCoords.add((double)xCoords.size());
		yCoords.add(this.getAverageFitness());
		xCoords1.add((double)xCoords1.size());
		yCoords1.add(this.getBestFitness());
		
		// sort the population (for scaling and elitism)

		// create a temporary vector to store new chromosomes
		Vector<Organism> vecNewOrganism = new Vector<Organism>();

		Vector<Organism> vecCopies = this.makeCopies(2, 2);
		if (vecCopies != null) {
			for (int i = 0; i < vecCopies.size(); i++) {
				// System.out.println(vecCopies.get(i).getBrain().getWeights());
				vecNewOrganism.add(vecCopies.get(i));
			}
		}

		// now we enter the GA loop

		// repeat until a new population is generated
		while (vecNewOrganism.size() < vecOrganism.size()) {
			// grab two chromosomes
			Organism parent1 = organismRoulette();
			Organism parent2 = organismRoulette();

			// create some offspring via crossover
			Organism child1 = this.breed(parent1, parent2);
			Organism child2 = this.breed(parent2, parent1);

			// now copy into vecNewPop population
			vecNewOrganism.add(child1);
			vecNewOrganism.add(child2);

		}
		// finished so assign new pop back into m_vecPop
		// System.out.println(this.getAverageFitness());
		//this.display();
		vecOrganism = vecNewOrganism;

		Vector<Double> inputs = new Vector<Double>();
		for (int i = 0; i < this.getVecOrganism().size(); i++) {
			double lowestDistance = Math.pow(2, 64);
			double lowestAngle = Math.pow(2, 64);
			double lowestFoodIndex = 0;
			for (int j = 0; j < this.vecFood.size(); j++) {
				if (VectorUtil.distance(((Minesweeper) this.getVecOrganism().get(i)).getX(), ((Minesweeper) this
						.getVecOrganism().get(i)).getY(), (double) this.vecFood.get(j).x,
						(double) this.vecFood.get(j).y) < lowestDistance) {
					lowestDistance = VectorUtil.distance(((Minesweeper) this.getVecOrganism().get(i)).getX(),
							((Minesweeper) this.getVecOrganism().get(i)).getY(), (double) this.vecFood.get(j).x,
							(double) this.vecFood.get(j).y);
					lowestAngle = VectorUtil.angle(((Minesweeper) this.getVecOrganism().get(i)).getX(),
							((Minesweeper) this.getVecOrganism().get(i)).getY(), (double) this.vecFood.get(j).x,
							(double) this.vecFood.get(j).y);
					lowestFoodIndex = j;
				}

			}
			double rotationInput = Math.toRadians(lowestAngle) - ((Minesweeper) this.getVecOrganism().get(i)).rotation;
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
			inputs.add((rotationInput) / (Math.PI));
			Vector<Double> rawInputs = new Vector<Double>();
			rawInputs.add(lowestDistance);
			rawInputs.add(rotationInput);
			this.getVecOrganism().get(i).setRawInput(rawInputs);
		}

	}

	public void sort() {
		for (int i = 1; i < this.vecOrganism.size(); i++) {
			for (int j = i; j > 0; j--) {
				if (this.vecOrganism.get(j).getFitness() < this.vecOrganism.get(j - 1).getFitness()) {
					Organism tempOrganism = this.vecOrganism.get(j);
					this.vecOrganism.set(j, this.vecOrganism.get(j - 1));
					this.vecOrganism.set(j - 1, tempOrganism);

				}
			}
		}

		/*
		 * System.out.print("["); for (int i = 1; i < this.vecOrganism.size();
		 * i++) { System.out.print(this.vecOrganism.get(i).getFitness()); if(i
		 * != this.vecOrganism.size()-1){ System.out.print(", "); } }
		 * System.out.println("]");
		 */

		/*
		 * System.out.print(this.vecOrganism.get(this.vecOrganism.size()).getFitness
		 * () + "\t"); System.out.println(this.getBestFitness());
		 */
	}

	public double getAverageFitness() {
		return this.getTotalFitness() / this.vecOrganism.size();
	}

	public double getTotalFitness() {
		double totalFitness = 0;
		for (int i = 0; i < this.vecOrganism.size(); i++) {
			totalFitness += this.vecOrganism.get(i).getFitness();
		}
		return totalFitness;
	}

	public Organism getBestGenome() {
		Organism bestGenome = new Organism(this.vecOrganism.get(0).getBrain().getNumInputs(), this.vecOrganism.get(0)
				.getBrain().getNumOutputs());
		for (int i = 0; i < this.vecOrganism.size(); i++) {
			if (this.vecOrganism.get(i).getFitness() > bestGenome.getFitness()) {
				bestGenome = this.vecOrganism.get(i);
			}
		}
		return bestGenome;
	}

	public double getWorstFitness() {
		double worstFitness = Math.pow(2, 64);
		for (int i = 0; i < this.vecOrganism.size(); i++) {
			if (this.vecOrganism.get(i).getFitness() < worstFitness) {
				worstFitness = this.vecOrganism.get(i).getFitness();
			}
		}
		return worstFitness;
	}

	public double getBestFitness() {
		double bestFitness = 0;
		for (int i = 0; i < this.vecOrganism.size(); i++) {
			if (this.vecOrganism.get(i).getFitness() > bestFitness) {
				bestFitness = this.vecOrganism.get(i).getFitness();
			}
		}
		return bestFitness;
	}

	public Vector<Organism> makeCopies(int numOrganisms, int numCopies) {
		// add the required amount of copies of the n most fittest
		// to the supplied vector
		if (this.vecOrganism.size() >= numCopies * numOrganisms) {
			Vector<Organism> vecCopies = new Vector<Organism>();
			this.sort();

			while (numOrganisms-- > 0) {
				for (int i = 0; i < numCopies; i++) {
					Organism selectedOrganism = this.vecOrganism.get((this.vecOrganism.size() - 1) - numOrganisms);
					// System.out.println(selectedOrganism.getFitness());
					Organism resetOrganism = new Minesweeper(new NeuralNet(selectedOrganism.getBrain().getWeights(), 2,
							2));
					vecCopies.add(resetOrganism);
				}
			}
			return vecCopies;
		}
		return null;
	}

	public Organism breed(Organism parent1, Organism parent2) {
		if (new Random().nextDouble() < CROSSOVER_RATE) {
			return parent1;
		}

		if (parent1.getBrain().getWeights().size() == parent2.getBrain().getWeights().size()) {

			int crossoverPoint = (int) (new Random().nextDouble() * parent1.getBrain().getWeights().size());
			Vector<Double> childWeights = new Vector<Double>();

			/*
			 * for (int i = 0; i < crossoverPoint; i++) {
			 * childWeights.add(parent1.getBrain().getWeights().get(i)); } for
			 * (int i = crossoverPoint; i <
			 * parent1.getBrain().getWeights().size(); i++) {
			 * childWeights.add(parent2.getBrain().getWeights().get(i)); }
			 */// USE THIS

			/*
			 * for (int i = 0; i < parent1.getBrain().getWeights().size(); i++)
			 * { double randomise = new Random().nextDouble(); if (randomise <
			 * 0.5) { childWeights.add(parent1.getBrain().getWeights().get(i));
			 * } else {
			 * childWeights.add(parent2.getBrain().getWeights().get(i)); } }
			 */// NOT THIS

			for (int i = 0; i < parent1.getBrain().getWeights().size(); i++) {
				childWeights.add(parent1.getBrain().getWeights().get(i));
			}

			Organism child1 = new Minesweeper(new NeuralNet(childWeights, parent1.getBrain().getNumInputs(), parent1
					.getBrain().getNumOutputs()));
			child1.mutate();
			return child1;
		} else {

			return parent1;
		}
	}

	public Organism organismRoulette() {
		double fitnessThreshold = new Random().nextDouble() * this.getTotalFitness();
		for (int i = 0; i < this.vecOrganism.size(); i++) {
			fitnessThreshold -= this.vecOrganism.get(i).getFitness();
			if (fitnessThreshold <= 0) {
				return new Minesweeper(this.vecOrganism.get(i).getBrain(), this.vecOrganism.get(i).getFitness());
			}
		}
		return new Minesweeper(this.vecOrganism.get(0).getBrain(), this.vecOrganism.get(0).getFitness());
	}

	public Vector<Organism> getVecOrganism() {
		return vecOrganism;
	}

	public void setVecOrganism(Vector<Organism> vecOrganism) {
		this.vecOrganism = vecOrganism;
	}

}
