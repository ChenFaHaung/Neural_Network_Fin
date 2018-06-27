package SOM;

import java.util.ArrayList;

public class som {
	int near;
	double side;
	double rmse;
	double learn;
	double times;
	double maxTemp;
	double minTemp;
	double reguTemp;
	double reguNu;
	double[] weight;
	double[][] vT;
	double[][] data;
	double[][] dataTrain;
	double[][] dataTest;
	Perceptron p[][];

	public double[][] initialData(ArrayList<double[]> temp) {
		System.out.println("initialize");
		data = new double[temp.size()][temp.get(0).length];

		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0; j < temp.get(0).length; j++) {
				data[i][j] = temp.get(i)[j];
			}
		}
		return data;
	}

	public void randomData() {// 記得不要丟迴圈刷新
		double[] exchange = new double[data[0].length];

		for (int i = 0; i < data.length * 3; i++) {
			int index1 = (int) (Math.random() * data.length);
			int index2 = (int) (Math.random() * data.length);
			exchange = data[index1];
			data[index1] = data[index2];
			data[index2] = exchange;
		}
	}

	public Perceptron[][] cal(double learnTemp, double timesTemp, int nearTemp, Perceptron temp[][], double scale) {
		p = temp;
		learn = learnTemp;
		times = timesTemp;
		near = nearTemp;
		side = scale * 1.05;
		double pGap = 2 * side / (p.length - 1);

		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p.length; j++) {

				p[i][j].randomWeight(data[0].length);
				p[i][j].setWeight(-side + i * pGap, -side + j * pGap);
			}
		}

		for (int i = 0; i < times; i++) {

			randomData();
			train(i);
			if (near > 0) {
				near--;
			}

		}
		test();
		return p;
	}

	public void train(int time) {// 隨時間改變
		double valueTemp = 0;
		for (int i = 0; i < data.length; i++) {
			int minR = 0;
			int minC = 0;
			double minValue = 10000000;

			for (int j = 0; j < p.length; j++) {
				for (int k = 0; k < p.length; k++) {
					valueTemp = p[j][k].getDistance(data[i]);
					if (valueTemp < minValue) {
						minValue = valueTemp;
						minR = j;
						minC = k;
					}
				}
			}
			for (int j = minR - near; j <= minR + near; j++) {
				for (int k = minC - near; k <= minC + near; k++) {
					if (j >= 0 && j < p.length && k >= 0 && k < p.length) {
						p[j][k].winnerLearn(learn * (1 - time / times), data[i]);
					}
				}
			}

		}
	}

	public void test() {
		double valueTemp = 0;

		for (int j = 0; j < p.length; j++) {
			for (int k = 0; k < p.length; k++) {

				p[j][k].initialWin();
			}
		}

		for (int i = 0; i < data.length; i++) {
			int minR = 0;
			int minC = 0;
			double minValue = 10000000;

			for (int j = 0; j < p.length; j++) {
				for (int k = 0; k < p.length; k++) {
					valueTemp = p[j][k].getDistance(data[i]);
					if (valueTemp < minValue) {
						minValue = valueTemp;
						minR = j;
						minC = k;
					}
				}
			}

			p[minR][minC].win();

		}
	}
}
