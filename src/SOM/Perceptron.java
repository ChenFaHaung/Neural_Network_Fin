package SOM;

public class Perceptron {
	int positionX;
	int positionY;
	int winTimes = 0;
	double a = 0.3;
	double delta;
	double output;
	double distance;
	double[] weight;
	double[] weightDelta;

	public void initialWin() {
		winTimes = 0;
	}

	public void win() {
		winTimes++;
	}

	public void randomWeight(int length) {
		weightDelta = new double[length + 1];
		weight = new double[length + 1];
		for (int i = 0; i < weight.length; i++) {
			weight[i] = Math.random() * 4 - 2;
		}
	}

	public void setWeight(double x, double y) {

		weight[1] = x;
		weight[2] = y;

	}

	public void setPosition(int x, int y) {

		positionX = x;
		positionY = y;

	}

	public double getDistance(double[] input) {
		double temp = 0;
		temp += Math.pow(weight[0] + 1, 2);
		for (int i = 1; i < weight.length; i++) {
			temp += Math.pow(weight[i] - input[i - 1], 2);
		}
		temp = Math.sqrt(temp);

		return temp;
	}

	public void winnerLearn(double learn, double[] input) {//調學習率
		weight[0] += learn * (-1 - weight[0]);
		for (int i = 1; i < weight.length; i++) {
			weight[i] += learn * (input[i - 1] - weight[i]);
		}
	}
}
