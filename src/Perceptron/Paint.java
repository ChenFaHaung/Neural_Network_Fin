package Perceptron;

import java.awt.*;
import java.util.ArrayList;

public class Paint extends Canvas {
	ArrayList<double[]> data = new ArrayList();
	int dotSize = 6;
	int enlarge = 100;
	double[] weight = new double[1];
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawLine(0, 320, 640, 320);
		g.drawLine(320, 0, 320, 640);
		if (data.get(0).length == 3) {
		//畫資料	
			if(weight.length==3){
				Graphics2D g2 = (Graphics2D)this.getGraphics();
				g2.setStroke(new BasicStroke(2));
				int line[] =getLine(weight);
				g2.setColor(Color.WHITE);
				g2.drawLine(line[0],line[1],line[2],line[3]);
			}
			for (int i = 0; i < data.size(); i++) {
				int[] temp = null;
				temp = Pos(data.get(i)[0], data.get(i)[1]);
				if (data.get(i)[2] == 1) {
					g.setColor(Color.green);
					g.drawString("X", temp[0], temp[1]);
				} else {
					g.setColor(Color.red);
					g.drawString("O", temp[0], temp[1]);
				}
			}
		}

	}

	public int[] Pos(double x, double y) {
		int[] temp = new int[2];
		temp[0] = (int) Math.floor(x * enlarge) + 320 - dotSize / 2;
		temp[1] = -(int) Math.floor(y * enlarge) + 328 - dotSize / 2;
		return temp;
	}

	public void drawData(ArrayList<double[]> temp) {
		double max = 0;
		for (int i = 0; i < temp.size(); i++) {
			if (Math.abs(temp.get(i)[0]) > max) {
				max = Math.abs(temp.get(i)[0]);
			}
			if (Math.abs(temp.get(i)[1]) > max) {
				max = Math.abs(temp.get(i)[1]);
			}
		}
		enlarge = (int) (300 / max);
		data = temp;
		repaint();
	}
	public Paint() {
		double[] fill = new double[1];
		data.add(fill);
	}
	public void drawLine(double[] temp) {
		weight = temp;
		repaint();
	}
	
	public int[] getLine(double[] temp) {
	//畫程式
		int ret[] = new int[4];
		double x = -1000;
		double y = (temp[0]-x*temp[1])/temp[2];
		ret[0] = (int) Math.floor(x * enlarge)+320;
		ret[1] = -(int) Math.floor(y * enlarge)+320 ;
		
		double endx = 1000;
		double endy = (temp[0]-endx*temp[1])/temp[2];
		
		ret[2] = (int) Math.floor(endx * enlarge)+320;
		ret[3] = -(int) Math.floor(endy * enlarge)+320;
		return ret;
	}
}
