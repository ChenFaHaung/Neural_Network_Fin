package Perceptron;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class pic implements ActionListener{
	static ArrayList<double[]> data;
	JFrame frame = null;
	JLabel labelFile = null;
	JLabel labelConvergence = new JLabel("收斂條件");
	JLabel labelLearn = new JLabel("學習率");
	JTextField inputConvergence = new JTextField(5);
	JTextField inputLearn = new JTextField(5);
	JLabel labalRate = new JLabel("辨識率 : ");
	JTextField outputRate = new JTextField(5);
	JPanel panel1 = new JPanel();
	Paint canvas = new Paint();
	JFileChooser fileChooser = null;
	File file = null;
	Perception perceptron = new Perception();
	public pic() {//GUI編排
		frame = new JFrame("Perceptron");
		Container contentPane = frame.getContentPane();
		canvas.setSize(640,640);
		JButton bSel = new JButton("選擇檔案");
		JButton bCal = new JButton("測試");
		outputRate.setEditable(false);
		outputRate.setText("0");
		inputLearn.setText("0.1");
		inputConvergence.setText("50");
		bSel.addActionListener(this);
		bCal.addActionListener(this);
		
		panel1.add(bSel);
		panel1.add(labelLearn);
		panel1.add(inputLearn);
		panel1.add(labelConvergence);
		panel1.add(inputConvergence);
		panel1.add(bCal);
		panel1.add(labalRate);
		panel1.add(outputRate);
		labelFile = new JLabel(" ", JLabel.CENTER);
		fileChooser = new JFileChooser();
		contentPane.add(canvas, BorderLayout.CENTER);
		contentPane.add(panel1, BorderLayout.NORTH);

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
//測試紐
		int result;
		if (e.getActionCommand().equals("測試")&&file!=null) {
			perceptron.initialData(Double.valueOf(inputLearn.getText()), Double.valueOf(inputConvergence.getText()));
			double[] w = perceptron.train();
			canvas.drawLine(w);
			String per  = "%";
			outputRate.setText(String.format("%.2f %s", perceptron.test(),per));
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
					canvas.drawData(data);//抓點的資料		
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			}
		}
	}
}
