package plot.main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		MainWindow win = new MainWindow("tanh",640,640);
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
