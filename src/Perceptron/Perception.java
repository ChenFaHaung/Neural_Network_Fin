package Perceptron;

import java.util.ArrayList;

public class Perception {
	ArrayList<double[]> data = new ArrayList();
	double learn;
	double convergence;
	double[][] Train;
	double[][] Test;
	double[] weight;
	double maxD;
	double minD;
	double tl = 0.1;
	double tc = 50;
	public void initialData(double lT, double cT) {
		learn = lT;
		convergence = cT;
		//打亂數據
		double[] exchange1 = new double[data.get(0).length];
		double[] exchange2 = new double[data.get(0).length];
		for (int i = 0; i < data.size()*3; i++) {
			int index1 = (int)(Math.random()*data.size());
			int index2 = (int)(Math.random()*data.size());
			exchange1 = data.get(index1);
			exchange2 = data.get(index2);
			data.set(index1,exchange2);
			data.set(index2,exchange1);
		}
		//資料切割
		Train = new double[data.size() / 3 * 2][weight.length + 1];
		Test = new double[data.size() - data.size() / 3 * 2][weight.length + 1];
		maxD = -100;
		minD = 100;
//找期望值為(0,1)或(1,2)
		for (int i = 0; i < Train.length; i++) {
			for (int j = 0; j < weight.length; j++) {
				Train[i][0] = -1;
				Train[i][j + 1] = data.get(i)[j];
			}
			if (Train[i][Train[i].length - 1] > maxD) {
				maxD = Train[i][Train[i].length - 1];
			}
			if (Train[i][Train[i].length - 1] < minD) {
				minD = Train[i][Train[i].length - 1];
			}
		}
		for (int i = Train.length; i < data.size(); i++) {
			for (int j = 0; j < weight.length; j++) {
				Test[i - Train.length][0] = -1;
				Test[i - Train.length][j + 1] = data.get(i)[j];
			}
			if (Test[i - Train.length][Test[i - Train.length].length - 1] > maxD) {
				maxD = Test[i - Train.length][Test[i
						- Train.length].length - 1];
			}
			if (Test[i - Train.length][Train[i - Train.length].length - 1] < minD) {
				minD = Test[i - Train.length][Test[i
						- Train.length].length - 1];
			}
		}
	}
	
	public void setData(ArrayList<double[]> temp) {
		data = temp;
		weight = new double[data.get(0).length];
	}

	public double[] train() {
		for (int i = 0; i < weight.length; i++) {
			weight[i] = (int) Math.random() * 2 - 1;
		}
		print(weight);
		if(learn == 0){
			learn = tl;
		}
		if(convergence == 0){
			convergence = tc;
		}
		boolean allGreen = false;
			
		double con = convergence;
		while (con>0) {
			if(allGreen){
				break;
			}
			allGreen = true;
			con--;
			double d = 0;
			double temp = 0;
			for (int i = 0; i < Train.length; i++) {
				temp = 0;
				for (int j = 0; j < Train[0].length - 1; j++) {
					temp += weight[j] * Train[i][j];
				}
				if (temp > 0) {
					d = maxD;
				} else {
					d = minD;
				}
				if (d != Train[i][Train[i].length - 1]) {
					double s =convergence-con;
					allGreen = false;
					if (d == maxD) {
						print(weight);
						for (int j = 0; j < weight.length; j++) {
							
							weight[j] = weight[j]-Train[i][j]* learn;
							
						}	
						print(weight);
					} else {
						print(weight);
						for (int j = 0; j < weight.length; j++) {
							weight[j] = weight[j]+Train[i][j]* learn;
						}
						print(weight);
					}
				}
			}
		}
		System.out.print("最後鍵結值 : ");
		print(weight);
		return weight;
	}

	public double test() {
		double d = 0;
		double temp = 0;
		double correct = 0;
		double wrong = 0;
		for (int i = 0; i < Test.length; i++) {
			temp = 0;
			for (int j = 0; j < Test[0].length - 1; j++) {
				temp += weight[j] * Test[i][j];
			}
			if (temp > 0) {
				d = maxD;
			} else {
				d = minD;
			}
			if (d != Test[i][Test[i].length - 1]) {
				wrong++;
			}else{
				correct++;
			}
		}
		System.out.println("測試辨識率： "+(correct/(correct+wrong)*100));
		return correct/(correct+wrong)*100;
	}
	public static void print(double[] temp) {
		for (int i = 0; i < temp.length; i++) {
			System.out.print(temp[i] + " ");
		}
		System.out.println();
	}
}

