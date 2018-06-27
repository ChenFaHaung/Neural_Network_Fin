package Maltilayer;

import java.util.ArrayList;

public class Mperceptron {

		ArrayList<double[]> data = new ArrayList();
		int exp;//期望值
		int convergence = 10000;//收斂次數
		double o;//輸出
		double c;//均方誤差
		double en;
		double up;//頂端
		double le;//學習率
		double Mm;//大小差
		double Ms;//標準化
		double max;
		double min;
		double aen;//全部en
		double aven;//平均en
		double down;
		double del1;
		double del2;
		double del3;
		double del4;
		double[] y;
		double[] d1;
		double[] d2;
		double[] d3;
		double[] d4;
		double[][] data2;
		public void initialData(double l) {
			le = l;
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
			data2 = new double [data.size()][data.get(0).length];
			
			for(int i = 0 ; i < data.size() ; i ++){
				for (int j = 0; j < data.get(0).length; j++) {
					data2[i][j] = data.get(i)[j];
				}
			}
			
			for(int j = 0 ; j < data2[0].length-1 ; j++){
				max = 0;
				min = 1000000000;
				for (int i = 0; i < data2.length ; i++) {
					if(data2[i][j] > max){
						max = data2[i][j];
					}
					if(data2[i][j] < min){
						min = data2[i][j];
					}
				}
				Mm = max - min;
				for (int i = 0; i < data2.length ; i++) {
					Ms = (data2[i][j] - min)/Mm;
					data2[i][j] = Ms;
				}
			}
			exp = (data.get(0).length-1);
			max = data.get(0)[exp];
			min = data.get(0)[exp];
			for(int i = 0 ; i < data.size() ; i ++){
				if(max < data.get(i)[exp]){
					max = data.get(i)[exp];
				}
				if(min > data.get(i)[exp]){
					min = data.get(i)[exp];
				}
			}
			
		}
		public void setData(ArrayList<double[]> temp) {
			data = temp;
		}
		public double Eav(){
			double alms = 0;
			for(int i = 0 ; i < data.size(); i++){
				y[0] = front(d1,i);
				y[1] = front(d2,i);
				y[2] = front(d3,i);
				o = frontp(d4, y);
				alms += lms(o, i);
			}
			return alms / data.size();
		}//均方差
		
		public double front(double[] w, int s){
			double ex = ((-1) * w[0]);
			for(int i = 0 ; i < exp ; i++){
				ex += (w[i+1] * data2[s][i]); 
			}
			ex = Math.exp(((-1) * ex));
			ex += 1;
			return (1/ex);
		}//前饋隱藏
		public double frontp(double[] w, double[] a){
			double result = (w[0] * (-1));
			for(int i = 0 ; i < a.length ; i++){
				result += (w[i+1] * a[i]); 
			}
			result = Math.exp(((-1) * result));
			result += 1;
			return (1/result);
		}//前饋輸出
		
		public double backp(int s){
			double result = (data.get(s)[exp] - min) / (max - min);
			result = result - o;
			result = result * o * (1-o);
			return result;
		}//倒傳遞輸出
		
		public double back(double w, double y, double delta){
			double result = delta * w;
			result = result * y * (1-y);
			return result;
		}//倒傳遞隱藏
		
		public double[] fweightp(double[] w, double delta){
			w[0] = w[0] + (le * delta * (-1));
			for(int i = 0 ; i < y.length ; i++){
				w[i+1] = w[i+1] + (le * delta * y[i]);
			}
			return w;
		}//修正輸出鍵結值
		
		public double[] fweight(double[]w, double delta, int k){
			w[0] = w[0] +(le * delta * (-1));
			for(int i = 0 ; i < exp ; i++){
				w[i+1] = w[i+1] + (le * delta * data2[k][i]);
			}
			return w;
		}//修正隱藏鍵結值
		
		public double [] Wr(double[]w){
			for(int j = 0; j < w.length; j++){
				w[j] = Math.random() * 4 - 2;
			}
			return w;
		}//鍵結值
		
		public double lms(double y, int s){
			double d = (data.get(s)[exp] - min) / (max - min);
			double result = y - d;
			result = result * result;
			result = result / 2;
			return result;		
		}//標準化
		
		public void train() {
			int con = 0;
			int k = 0;
			aven = 100;
			d1 = new double[data.get(0).length];
			d2 = new double[data.get(0).length];
			d3 = new double[data.get(0).length];
			d4 = new double[4];
			y = new double[3];
			d1 = Wr(d1);
			d2 = Wr(d2);
			d3 = Wr(d3);	
			d4 = Wr(d4);
			while(convergence > con && aven > c){
				aen = 0;
				while(k < (data.size()*2/3)){
					y[0] = front(d1, k);
					y[1] = front(d2, k);
					y[2] = front(d3, k);
					o = frontp(d4, y);
					up = (data.get(k)[exp]-min+1)/(max-min+1);
					down = (data.get(k)[exp]-min)/(max-min+1);
					
					en = lms(o, k);
					
					if(en > c){
						del4 = backp(k);
						del1 = back(y[0], del4, d4[1]);
						del2 = back(y[1], del4, d4[2]);
						del3 = back(y[2], del4, d4[3]);
						
						d1 = fweight(d1, del1, k);
						d2 = fweight(d2, del2, k);
						d3 = fweight(d3, del3, k);
						d4 = fweightp(d4, del4);
					}
					aen += lms(o, k);
					k++;
				}
				aven = aen / (data.size()*2/3);
				k = 0;
				con++;
			}
		}
//倒傳遞演算法，調鍵結值
		public double trainTest() {
			int count = 0;
			for(int i = 0 ; i < (data.size()*2/3); i++){
				y[0] = front(d1,i);
				y[1] = front(d2,i);
				y[2] = front(d3,i);
				o = frontp(d4, y);
				up = (data.get(i)[exp]-min+1)/(max-min+1);
				down = (data.get(i)[exp]-min)/(max-min+1);
				if(o > down && o < up){
					count++;
				}
			}
			double success = (double)count / (double)(data.size() * 2/3);
			return success * 100;
		}
//訓練辨識率
		
		public double all() {
			int count = 0;
			for(int i = 0 ; i < data.size(); i++){
				y[0] = front(d1,i);
				y[1] = front(d2,i);
				y[2] = front(d3,i);
				o = frontp(d4, y);
				up = (data.get(i)[exp]-min+1)/(max-min+1);
				down = (data.get(i)[exp]-min)/(max-min+1);
				if(o > down && o < up){
					count++;
				}
			}
			double success = (double)count / (double)data.size();
			return success * 100;
		}
//辨識率
		public static void print(double[] temp) {
			for (int i = 0; i < temp.length; i++) {
				System.out.print(temp[i] + " ");
			}
			System.out.println();
		}
	}