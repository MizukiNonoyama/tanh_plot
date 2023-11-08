package plot.utils;

public class MathHelper {
	public static int makeX(double x, double min_x,double max_x,int width,int posX) {
		return (int)((x - min_x) / (Math.abs(min_x) + Math.abs(max_x)) * width) + posX;
	}
	
	public static int makeY(double y, double min_y,double max_y,int height,int posY) {
		return (int)((max_y - y) / (Math.abs(min_y) + Math.abs(max_y)) * height) + posY;
	}
}
