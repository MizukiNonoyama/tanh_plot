package plot.main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import plot.panels.JPlot;
import plot.panels.PanelString;
import plot.utils.Pair;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ChangeListener {
	
	private PanelString string = new PanelString("y = tanh(cx), c = ", 500, 550, 2);
	private JPlot shape = new JPlot(new ArrayList<Pair<Double,Double>>(), false, string, -5,-5,5,5,1,1,0.1,0.1,70,70,500,500);
	
	
	public MainWindow(String title, int sizeX,int sizeY) {
		this.setSize(sizeX, sizeY);
		this.setTitle(title);
		JSlider c_slider = new JSlider(JSlider.HORIZONTAL,1,100,10);
		c_slider.addChangeListener(this);
		this.add(c_slider,BorderLayout.PAGE_END);
		double c = c_slider.getValue() / 10.0;
		this.shape.setData(getTanh(c));
		this.string.setString("y = tanh(cx), c = " + c);
		this.shape.setTitle(string);
		this.add(shape);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof JSlider) {
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		    	double c = (double)source.getValue() / 10.0;
		    	this.remove(shape);
		    	this.shape.setData(getTanh(c));
				this.string.setString("y = tanh(cx), c = " + c);
				this.add(shape);
				shape.setTitle(string);;
				this.repaint();
		    }
		}
	}
	
	private static List<Pair<Double,Double>> getTanh(double c) {
		List<Pair<Double,Double>> list = new ArrayList<Pair<Double,Double>>();
		for(int x = -500;x <= 500;x++) {
			list.add(new Pair<Double,Double>((double)x / 100.0, Math.tanh(c * (double)x / 100.0)));
		}
		return list;
	}
}
