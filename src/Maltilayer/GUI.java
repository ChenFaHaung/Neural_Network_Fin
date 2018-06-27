package Maltilayer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Perceptron.Paint;
import Perceptron.Perception;

public class GUI extends Canvas implements ActionListener{
	static ArrayList<double[]> data;
	JFrame frame = null;
	JLabel labelFile = null;
	JLabel lel = new JLabel("學習率");
	JLabel lroot = new JLabel("均方根誤差");
	JLabel lLearn = new JLabel("訓練辨識率");
	JLabel lTest = new JLabel("測試辨識率");
	JTextField letf = new JTextField(5);
	JTextField root = new JTextField(5);
	JTextField Learn = new JTextField(5);
	JTextField Test = new JTextField(5);
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	//paint canvas = new paint();
	JFileChooser fileChooser = null;
	File file = null;
	Mperceptron perceptron = new Mperceptron();
	public GUI() {//GUI編排
		frame = new JFrame("MultiPerceptron");
		Container contentPane = frame.getContentPane();
		setSize(640,640);
		JButton bSel = new JButton("選擇檔案");
		JButton bCal = new JButton("Run");
		letf.setText("0.1");
		root.setEditable(false);
		Learn.setEditable(false);
		Test.setEditable(false);
		bSel.addActionListener(this);
		bCal.addActionListener(this);
		
		panel2.add(bSel);
		panel2.add(lel);
		panel2.add(letf);
		panel2.add(bCal);
		panel1.add(lroot);
		panel1.add(root);
		panel1.add(lLearn);
		panel1.add(Learn);
		panel1.add(lTest);
		panel1.add(Test);
		labelFile = new JLabel(" ", JLabel.CENTER);
		fileChooser = new JFileChooser();
		contentPane.add(panel2, BorderLayout.NORTH);
		contentPane.add(panel1, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int result;
		if (e.getActionCommand().equals("Run")&&file!=null) {
			perceptron.initialData(Double.valueOf(letf.getText()));
			perceptron.train();
			String per  = "%";
			root.setText(String.format("%.6f", perceptron.Eav()));
			Learn.setText(String.format("%.2f %s", perceptron.trainTest(),per));
			Test.setText(String.format("%.2f %s", perceptron.all(),per));
		}
		
		if (e.getActionCommand().equals("選擇檔案")) {
			fileChooser.setDialogTitle("選擇檔案");
			result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION ) {
				file = fileChooser.getSelectedFile();
			
			try {
				data = new ArrayList();
				Scanner input = new Scanner(file);
				
				while (input.hasNext()) {	
					String tempInput= input.nextLine();
					String[] tempStr = tempInput.split("\\s+");
				    double[] temp = null; 
				//切割字串
					if(tempStr[0].equals("")){
						int space = 1;
						while(tempStr[space].equals("")){
							space++;
						}						
						temp = new double[tempStr.length-space];
						for(int i = space ; i <tempStr.length ; i++){
							temp[i-space] = Double.valueOf(tempStr[i]);
						}
					}else if(!tempStr[0].equals("")){
						temp = new double[tempStr.length];
						for(int i = 0 ; i <tempStr.length ; i++){
							temp[i] = Double.valueOf(tempStr[i]);
						}
					}
					data.add(temp);					
				}				
				perceptron.setData(data);//抓測試資料	
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			}
		}
	
	}
}
