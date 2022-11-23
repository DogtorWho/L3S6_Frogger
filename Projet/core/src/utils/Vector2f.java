package utils;

public class Vector2f {
	private double x;
	private double y;
	
	public Vector2f(double x, double y) {
		setVector2f(x, y);
	}
	
	public void setVector2f(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVector2f(Vector2f v) {
		this.x = v.getX();
		this.y = v.getY();
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2f [x=" + x + ", y=" + y + "]";
	}
}
