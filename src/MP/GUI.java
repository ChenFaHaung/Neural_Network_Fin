package MP;

import javax.swing.*;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class GUI implements ActionListener {
	final int nodeSize = 50;
	final int layerSize = 60;
	final int layerGap = 80;
	int maxLabel = 5;
	static ArrayList<double[]> data;
	static ArrayList<Double> kind;
	int outputNum;
	Perceptron[][] Nodes;
	int layerNum = 0;
	int nodeNum = 0;
	JFrame frame;

	JButton btnSelect;
	JButton btnCal;

	JButton btnIniLayer;
	JButton btnAddLayer;
	JButton btnDelLayer;

	JPanel panelTOP;
	double[] panelLayer;
	JPanel panelBOT;
	JPanel jp;

	JLabel labelFile;
	JLabel labelConvergence;
	JLabel labelLearn;
	JLabel labalRate;
	double[][] nodeLabel;

	JTextField inputConvergence;
	JTextField inputLearn;

	JTextField outputRate;
	JFileChooser fileChooser;
	File file;
	JPopupMenu popupmenu;
	JScrollPane scroller;
	JMenuItem node;
	JMenuItem test;
	int select;

	Mperceptron mlp = new Mperceptron();

	GUI() {
		select = 0;
		panelLayer = new double[2];
		nodeLabel = new double[2][];
		//panelLayer = new JPanel[0];
		// frame
		frame = new JFrame();
		frame.setTitle("MLP");
		frame.setSize(640, 100);
		frame.setLayout(new BorderLayout());

		// btn
		btnSelect = new JButton("選擇檔案");
		btnSelect.addActionListener(this);
		btnCal = new JButton("開始測試");
		btnCal.addActionListener(this);
		// label
		labelFile = new JLabel();
		labelConvergence = new JLabel("均方根誤差: ");
		labelLearn = new JLabel("學習率 : ");
		labalRate = new JLabel("辨識率 : ");
		// popup

		//popupmenu = new JPopupMenu();
		//popupmenu.add(node = new JMenuItem("設定神經元數"));
	
		//node.addActionListener(this);
	
		// file
		fileChooser = new JFileChooser();

		// text
		inputConvergence = new JTextField(5);
		inputConvergence.setEditable(false);
		inputConvergence.setText("0");

		outputRate = new JTextField(5);
		outputRate.setEditable(false);
		outputRate.setText("0");

		inputLearn = new JTextField(5);
		inputLearn.setText("0.5");

		// panel
		panelBOT = new JPanel();
		panelBOT.add(labelLearn);
		panelBOT.add(inputLearn);
		panelBOT.add(labelConvergence);
		panelBOT.add(inputConvergence);
		panelBOT.add(labalRate);
		panelBOT.add(outputRate);
		panelBOT.add(btnSelect);
		panelBOT.add(btnCal);
		//panelBOT.add(btnIniLayer);

		//panelBOT.setBorder(BorderFactory.createTitledBorder("設定"));
		//panelBOT.setPreferredSize(new Dimension(580, 100));

		//panelTOP = new JPanel();
		//panelTOP.setBorder(BorderFactory.createTitledBorder("神經元"));
		//panelTOP.setPreferredSize(new Dimension(580, 340)); // 这里设置才有用
		//scroller = new JScrollPane(panelTOP);

		// frame

		//frame.setResizable(false);
		//frame.addWindowListener(new WindowHandler());
		//frame.add(popupmenu);
		//frame.add(labelFile, BorderLayout.NORTH);
		//frame.add(scroller, BorderLayout.CENTER);
		frame.add(panelBOT, BorderLayout.NORTH);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	/*public void iniNode(int num) {
		if (num > maxLabel) {
			maxLabel = num;
			panelTOP.setPreferredSize(new Dimension((panelLayer.length)
					* layerGap, layerSize * maxLabel));
			for (int i = 0; i < panelLayer.length; i++) {
				panelLayer[i].setPreferredSize(new Dimension(layerSize,
						layerSize * maxLabel));
			}
		}

		for (int i = 0; i < nodeLabel[select].length; i++) {
			panelLayer[select].remove(nodeLabel[select][i]);
		}

		nodeLabel[select] = new JLabel[num];

		for (int i = 0; i < num; i++) {
			nodeLabel[select][i] = new JLabel();
			nodeLabel[select][i].setPreferredSize(new Dimension(nodeSize,
					nodeSize));
			nodeLabel[select][i].setBorder(BorderFactory.createLineBorder(
					Color.BLACK, 2));
			nodeLabel[select][i].setText("      " + (i + 1));
			panelLayer[select].add(nodeLabel[select][i]);
		}

		frame.setVisible(false);
		frame.setVisible(true);
	}
*/
	/*public void iniLayer(int num) {

		for (int i = 0; i < panelLayer.length; i++) {
			panelTOP.remove(panelLayer[i]);
		}
		nodeLabel = new JLabel[num + 1][0];
		panelLayer = new JPanel[num + 1];
		panelTOP.setPreferredSize(new Dimension((panelLayer.length) * layerGap,
				layerSize * maxLabel)); 

		for (int i = num; i > -1; i--) {
			panelLayer[i] = new JPanel();
			if (i == 0) {
				panelLayer[i]
						.setBorder(BorderFactory.createTitledBorder("輸出層"));
				panelLayer[i].setPreferredSize(new Dimension(layerSize, layerSize
						* maxLabel));
			} else {
				panelLayer[i].setBorder(BorderFactory.createTitledBorder("第"
						+ (i) + "層"));
				panelLayer[i].setPreferredSize(new Dimension(layerSize, layerSize
						* maxLabel));

				panelLayer[i].addMouseListener(new MouseAdapter() {

					public void mousePressed(MouseEvent evt) {
						for (int j = 0; j < panelLayer.length; j++) {
							if (panelLayer[j] == evt.getSource()) {
								select = j;
							}
						}
						int mods = evt.getModifiers();
						if (evt.isPopupTrigger()
								&& (mods & InputEvent.BUTTON3_MASK) != 0) {
							popupmenu.show(evt.getComponent(), evt.getX(),
									evt.getY());
						}
					}

					public void mouseReleased(MouseEvent evt) {
						for (int j = 0; j < panelLayer.length; j++) {
							if (panelLayer[j] == evt.getSource()) {
								select = j;
							}
						}
						int mods = evt.getModifiers();
						if (evt.isPopupTrigger()
								&& (mods & InputEvent.BUTTON3_MASK) != 0) {
							popupmenu.show(evt.getComponent(), evt.getX(),
									evt.getY());

						}
					}

				});
			}

			
			panelTOP.add(panelLayer[i]);
		}
		//frame.add(scroller);
		frame.setVisible(false);
		frame.setVisible(true);

		for (int i = 0; i < panelLayer.length; i++) {
			select = i;
			iniNode(1);
		}
	}
*/
	public void actionPerformed(ActionEvent e) {

		int result;

		if (e.getActionCommand().equals("開始測試") && file != null) {
			select = 0;
			Nodes = new Perceptron[3][];
			for (int i = 0; i < 3; i++) {
				Nodes[i] = new Perceptron[3];
				for (int j = 0; j < 3; j++) {
					Nodes[i][j] = new Perceptron();
				}
			}
			
			double r = mlp.cal(Double.valueOf(inputLearn.getText()),Nodes, kind);
			String per = "%";
			outputRate.setText(String.format("%.2f %s", r, per));
			inputConvergence.setText(String.format("%.6f", mlp.rmse));
		}

		if (e.getActionCommand().equals("選擇檔案")) {
			fileChooser.setDialogTitle("選擇檔案");
			result = fileChooser.showOpenDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				//labelFile.setText("檔案名稱：" + file.getName());
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
					outputNum=1;
					int temp = 2;
					while(temp<kind.size()){
						outputNum++;
						temp*=2;
					}

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			mlp.initialData(data);
		}
	}

}

class WindowHandler extends WindowAdapter { 
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}