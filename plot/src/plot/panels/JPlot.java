package plot.panels;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import plot.utils.MathHelper;
import plot.utils.Pair;
import plot.utils.PlotField;

@SuppressWarnings("serial")
public class JPlot extends JPanel {
	private List<Pair<Double,Double>> datas;
	private double min_x;
	private double min_y;
	private double max_x;
	private double max_y;
	private double scale_x;
	private double scale_y;
	private double scale_x_m;
	private double scale_y_m;
	private final int width;
	private final int height;
	private final int pos_x;
	private final int pos_y;
	private boolean makeLine;
	private PanelString title;
	public JPlot(List<Pair<Double,Double>> datas,boolean makeLine, PanelString title, double min_x,double min_y,double max_x,double max_y,double scale_x, double scale_y, double scale_x_small, double scale_y_small, int posx,int posy, int width,int height) {
		this.datas = new ArrayList<Pair<Double,Double>>(datas);
		this.min_x = min_x;
		this.min_y = min_y;
		this.max_x = max_x;
		this.max_y = max_y;
		this.width = width;
		this.height = height;
		this.pos_x = posx;
		this.pos_y = posy;
		this.makeLine = makeLine;
		this.scale_x = scale_x;
		this.scale_y = scale_y;
		this.scale_x_m = scale_x_small;
		this.scale_y_m = scale_y_small;
		this.title = title;
	}
	
	public void setData(List<Pair<Double,Double>> datas) {
		this.datas = new ArrayList<Pair<Double,Double>>(datas);
	}
	
	public void setTitle(PanelString title) {
		this.title = title;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        PlotField pf = new PlotField(min_x,min_y,max_x,max_y,pos_x,pos_y,width,height);
        this.drawAxis(g, pf);
        this.drawPlots(g, pf, this.makeLine, this.datas);
        this.title.paintComponent(g);
    }
	
	private void drawPlots(Graphics g, PlotField pf, boolean mkLine,List<Pair<Double,Double>> datas) {
		int size = datas.size();
		for(int index = 0;index < size;index++) {
			Pair<Double,Double> pair = datas.get(index);
			if(mkLine) {
				if(index + 1 < size) {
					Pair<Double,Double> pair1 = datas.get(index + 1);
					if(MathHelper.isIn(pair.getKey(), pair.getValue(), min_x,min_y,max_x,max_y) && MathHelper.isIn(pair1.getKey(), pair1.getValue(), min_x,min_y,max_x,max_y)){
						g.drawLine(pf.getX(pair.getKey()), pf.getY(pair.getValue()), pf.getX(pair1.getKey()), pf.getY(pair1.getValue()));
					}
				}
			}
			else {
				if(MathHelper.isIn(pair.getKey(), pair.getValue(), min_x,min_y,max_x,max_y)) {
					g.drawLine(pf.getX(pair.getKey()), pf.getY(pair.getValue()), pf.getX(pair.getKey()), pf.getY(pair.getValue()));
				}
			}
		}
	}
	
	private void drawAxis(Graphics g, PlotField pf) {
		//Axis X
		if(min_y < 0 && max_y > 0) {
			int y0 = pf.getY(0);
			String axis = "x";
			g.drawChars(axis.toCharArray(), 0, axis.length(), pf.getX(max_x), y0 - 10);
			g.drawLine(pf.getX(min_x), y0, pf.getX(max_x), y0);
			
			int min = this.min_x / this.scale_x > 0 ? MathHelper.roundUp(this.min_x / this.scale_x) : MathHelper.roundDown(this.min_x / this.scale_x);
			int max = this.max_x / this.scale_x < 0 ? MathHelper.roundUp(this.max_x / this.scale_x) : MathHelper.roundDown(this.max_x / this.scale_x);
			for(int i = min;i <= max;i++) {
				double scaleX = this.scale_x * i;
				if(i != 0) { 
					g.drawLine(pf.getX(scaleX), y0 - 5, pf.getX(scaleX), y0 + 5);
					String s = String.valueOf(scaleX);
					g.drawChars(s.toCharArray(), 0, s.length(), pf.getX(scaleX) - g.getFontMetrics().stringWidth(s) / 2, y0 + 15);
				}
				
				if((scaleX + scale_x) > this.max_x) continue;
				int min_m = scaleX / this.scale_x_m > 0 ? MathHelper.roundUp(scaleX / this.scale_x_m) : MathHelper.roundDown(scaleX / this.scale_x_m);
				int max_m = (scaleX + scale_x) / this.scale_x_m < 0 ? MathHelper.roundUp((scaleX + scale_x) / this.scale_x_m) : MathHelper.roundDown((scaleX + scale_x) / this.scale_x_m);
				for(int j = min_m + 1;j < max_m;j++) {
					double scaleX_m = this.scale_x_m * j;
					g.drawLine(pf.getX(scaleX_m), y0 - 2, pf.getX(scaleX_m), y0 + 2);
				}
			}
			
			int min_m_min = this.min_x / this.scale_x_m > 0 ? MathHelper.roundUp(this.min_x / this.scale_x_m) : MathHelper.roundDown(this.min_x / this.scale_x_m);
			int max_m_min = min * this.scale_x / this.scale_x_m < 0 ? MathHelper.roundUp(min * this.scale_x / this.scale_x_m) : MathHelper.roundDown(min * this.scale_x / this.scale_x_m);
			for(int j = min_m_min;j < max_m_min;j++) {
				double scaleX_m = this.scale_x_m * j;
				g.drawLine(pf.getX(scaleX_m), y0 - 2, pf.getX(scaleX_m), y0 + 2);
			}
			
			int min_m_max = max * this.scale_x / this.scale_x_m > 0 ? MathHelper.roundUp(max * this.scale_x / this.scale_x_m) : MathHelper.roundDown(max * this.scale_x / this.scale_x_m);
			int max_m_max = this.max_x / this.scale_x_m < 0 ? MathHelper.roundUp(this.max_x / this.scale_x_m) : MathHelper.roundDown(this.max_x / this.scale_x_m);
			for(int j = min_m_max + 1;j <= max_m_max;j++) {
				double scaleX_m = this.scale_x_m * j;
				g.drawLine(pf.getX(scaleX_m), y0 - 2, pf.getX(scaleX_m), y0 + 2);
			}
		}
			
		//Axis Y
		if(min_x < 0 && max_x > 0) {
			int x0 = pf.getX(0);
			g.drawLine(x0, pf.getY(this.min_y), x0, pf.getY(this.max_y));
			String axis = "y";
			g.drawChars(axis.toCharArray(), 0, axis.length(), x0 + 10, pf.getY(max_y) - g.getFontMetrics().stringWidth(axis));
			
			int min = this.min_y / this.scale_y > 0 ? MathHelper.roundUp(this.min_y / this.scale_y) : MathHelper.roundDown(this.min_y / this.scale_y);
			int max = this.max_y / this.scale_y < 0 ? MathHelper.roundUp(this.max_y / this.scale_y) : MathHelper.roundDown(this.max_y / this.scale_y);
			for(int i = min;i <= max;i++) {
				double scaleY = this.scale_y * i;
				if(i != 0) { 
					g.drawLine(x0 - 5, pf.getY(scaleY), x0 + 5, pf.getY(scaleY));
					String s = String.valueOf(scaleY);
					g.drawChars(s.toCharArray(), 0, s.length(), x0 - g.getFontMetrics().stringWidth(s) / 2 - 16, pf.getY(scaleY) + 5);
				}
				
				if((scaleY + scale_y) > this.max_y) continue;
				int min_m = scaleY / this.scale_y_m > 0 ? MathHelper.roundUp(scaleY / this.scale_y_m) : MathHelper.roundDown(scaleY / this.scale_y_m);
				int max_m = (scaleY + scale_y) / this.scale_y_m < 0 ? MathHelper.roundUp((scaleY + scale_y) / this.scale_y_m) : MathHelper.roundDown((scaleY + scale_y) / this.scale_y_m);
				for(int j = min_m + 1;j < max_m;j++) {
					double scaleY_m = this.scale_y_m * j;
					g.drawLine(x0 - 2, pf.getY(scaleY_m), x0 + 2, pf.getY(scaleY_m));
				}
			}
			
			int min_m_min = this.min_y / this.scale_y_m > 0 ? MathHelper.roundUp(this.min_y / this.scale_y_m) : MathHelper.roundDown(this.min_y / this.scale_y_m);
			int max_m_min = min * this.scale_y / this.scale_y_m < 0 ? MathHelper.roundUp(min * this.scale_y / this.scale_y_m) : MathHelper.roundDown(min * this.scale_y / this.scale_y_m);
			
			for(int j = min_m_min;j < max_m_min;j++) {
				double scaleY_m = this.scale_y_m * j;
				g.drawLine(x0 - 2, pf.getY(scaleY_m), x0 + 2, pf.getY(scaleY_m));
			}
			
			int min_m_max = max * this.scale_y / this.scale_y_m > 0 ? MathHelper.roundUp(max * this.scale_y / this.scale_y_m) : MathHelper.roundDown(max * this.scale_y / this.scale_y_m);
			int max_m_max = this.max_y / this.scale_y_m < 0 ? MathHelper.roundUp(this.max_y / this.scale_y_m) : MathHelper.roundDown(this.max_y / this.scale_y_m);
			
			for(int j = min_m_max + 1;j <= max_m_max;j++) {
				double scaleY_m = this.scale_y_m * j;
				g.drawLine(x0 - 2, pf.getY(scaleY_m), x0 + 2, pf.getY(scaleY_m));
			}
		}
		
		if(min_y < 0 && max_y > 0 && min_x < 0 && max_x > 0) {
			int x0 = pf.getX(0);
	        int y0 = pf.getY(0);
	        String s = "0";
			g.drawChars(s.toCharArray(), 0, s.length(), x0 - 10, y0 + 15);
		}
	}
}
