package plot.main;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import plot.config.Config;
import plot.panels.JPlot;
import plot.panels.PanelString;
import plot.utils.IFunction;
import plot.utils.MathHelper;
import plot.utils.Pair;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ChangeListener {
	
	private PanelString string = new PanelString("y = tanh(x / c),  c = ", 500, 550, 2);
	private JPlot shape = new JPlot(new ArrayList<Pair<Double,Double>>(), true, string, 
			Config.min_plot_x, Config.min_plot_y, Config.max_plot_x, Config.max_plot_y, 
			Config.scale_x, Config.scale_y, Config.scale_x_small, Config.scale_y_small, 70, 70, 500, 500);
	
	public MainWindow(String title, int sizeX, int sizeY) {
		this.setSize(sizeX, sizeY);
		this.setTitle(title);
		JSlider c_slider = new JSlider(JSlider.HORIZONTAL,1,100,10);
		c_slider.addChangeListener(this);
		this.add(c_slider,BorderLayout.PAGE_END);
		double c = c_slider.getValue() / 10.0;
		this.shape.setData(MathHelper.getFunction(new Tanh(c)));
		this.string.setString("y = tanh(x / c),  c = " + c);
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
		    	this.shape.setData(MathHelper.getFunction(new Tanh(c)));
				this.string.setString("y = tanh(x / c),  c = " + c);
				this.add(shape);
				shape.setTitle(string);;
				this.repaint();
		    }
		}
	}
	
	public static class Tanh implements IFunction {
		private double c;
		
		public Tanh(double value) {
			this.c = value;
		}

		@Override
		public double f(double x) {
			return Math.tanh(x / this.c);
		}
	}
}
