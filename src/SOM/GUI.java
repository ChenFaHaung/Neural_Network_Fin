package SOM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	JFrame frame;
	JButton btnSelect;
	JButton btnCal;
	JButton btnDV;
	JButton btnLN;
	JButton btnWin;
	JPanel panelTOP;
	JPanel[] panelLayer;
	JPanel panelBOT;
	JPanel jp;
	JLabel labelFile;
	JLabel labelNear;
	JLabel labelLearn;
	JLabel labalTimes;
	JLabel labalNum;
	JLabel[][] nodeLabel;
	JTextField inputNear;
	JTextField inputLearn;
	JTextField inputTimes;
	JTextField inputNum;
	JFileChooser fileChooser;
	File file;
	Paint canvas = new Paint();
	double scale;
	static ArrayList<double[]> data;
	static double[][] reguData;
	static ArrayList<Double> kind;
	int outputNum;
	Perceptron[][] Nodes;
	som som = new som();

	GUI() {
		panelLayer = new JPanel[0];
		// frame
		frame = new JFrame();
		frame.setTitle("SOM");
		frame.setSize(645, 820);
		frame.setLayout(new BorderLayout());

		// button
		btnSelect = new JButton("File");
		btnSelect.addActionListener(this);
		btnCal = new JButton("Test");
		btnCal.addActionListener(this);
		btnDV = new JButton("Data");
		btnDV.addActionListener(this);
		btnWin = new JButton("Win Time");
		btnWin.addActionListener(this);

		// label
		labelFile = new JLabel();
		labelNear = new JLabel("Near: ");
		labelLearn = new JLabel("Learn: ");
		labalTimes = new JLabel("Iteration: ");
		labalNum = new JLabel("Neuron: ");

		fileChooser = new JFileChooser();

		inputNear = new JTextField(5);
		inputNear.setText("5");
		inputTimes = new JTextField(5);
		inputTimes.setText("1000");
		inputLearn = new JTextField(5);
		inputLearn.setText("0.01");
		inputNum = new JTextField(5);
		inputNum.setText("10");

		panelBOT = new JPanel();
		panelBOT.add(btnSelect);
		panelBOT.add(labelLearn);
		panelBOT.add(inputLearn);
		panelBOT.add(labelNear);
		panelBOT.add(inputNear);
		panelBOT.add(labalTimes);
		panelBOT.add(inputTimes);
		panelBOT.add(labalNum);
		panelBOT.add(inputNum);
		panelBOT.add(btnCal);
		panelBOT.add(btnDV);
		panelBOT.add(btnWin);
		panelBOT.add(labelFile);

		panelBOT.setPreferredSize(new Dimension(580, 80));
		frame.addWindowListener(new WindowHandler());
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(panelBOT, BorderLayout.NORTH);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		int result;
		if (e.getActionCommand().equals("Data") && file != null) {
			canvas.setDVisible();
		}
		if (e.getActionCommand().equals("Win Time") && file != null) {
			canvas.setWVisible();
		}
		if (e.getActionCommand().equals("Test") && file != null) {
			Nodes = new Perceptron[Integer.valueOf(inputNum.getText())][Integer.valueOf(inputNum.getText())];

			for (int i = 0; i < Nodes.length; i++) {
				for (int j = 0; j < Nodes.length; j++) {
					Nodes[i][j] = new Perceptron();
				}
			}
			Nodes = som.cal(Double.valueOf(inputLearn.getText()), Double.valueOf(inputTimes.getText()),
					Integer.valueOf(inputNear.getText()), Nodes, scale);
			canvas.drawNode(Nodes);
		}

		if (e.getActionCommand().equals("File")) {
			fileChooser.setDialogTitle("File");
			result = fileChooser.showOpenDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				labelFile.setText("File name： " + file.getName());
				kind = new ArrayList<>();
				try {
					data = new ArrayList();
					Scanner input = new Scanner(file);

					while (input.hasNext()) {

						String tempInput = input.nextLine();
						String[] tempStr = tempInput.split("\\s+");
						double[] temp = null;

						if (tempStr[0].equals("")) {
							int space = 1;
							while (tempStr[space].equals("")) {
								space++;
							}

							temp = new double[tempStr.length - space];
							for (int i = space; i < tempStr.length; i++) {
								temp[i - space] = Double.valueOf(tempStr[i]);
							}
						} else if (!tempStr[0].equals("")) {
							temp = new double[tempStr.length];
							for (int i = 0; i < tempStr.length; i++) {
								temp[i] = Double.valueOf(tempStr[i]);
							}
						}
						if (kind.isEmpty()) {
							kind.add(temp[temp.length - 1]);
						}
						for (int i = 0; i < kind.size(); i++) {
							if (temp[temp.length - 1] == kind.get(i)) {
								break;
							}
							if (i == kind.size() - 1) {
								kind.add(temp[temp.length - 1]);
							}
						}

						data.add(temp);

					}
					outputNum = 1;
					int temp = 2;
					while (temp < kind.size()) {
						outputNum++;
						temp *= 2;
					}

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			reguData = som.initialData(data);
			scale = canvas.drawData(reguData);
		}
	}
}

class WindowHandler extends WindowAdapter {
	public void windowClosing(WindowEvent e) {

		System.exit(0);
	}
}
