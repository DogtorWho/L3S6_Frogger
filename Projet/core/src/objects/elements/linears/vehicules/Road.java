package objects.elements.linears.vehicules;

import utils.Vector2f;

public class Road {
	private Vector2f pos;
	private int line_number;
	private double rotation;
	private double speed;
	private double interval_min;
	private double interval_max;
	
	public Road(double x, double y, int num, double rota, double spd, double min, double max) {
		pos = new Vector2f(x, y);
		line_number = num;
		rotation = rota;
		speed = spd;
		interval_min = min;
		interval_max = max;
	}

	public Vector2f getPos() {
		return pos;
	}
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}
	public void setPos(double x, double y) {
		pos.setX(x);
		pos.setY(y);
	}
	public int getLine_number() {
		return line_number;
	}
	public double getRotation() {
		return rotation;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getInterval_min() {
		return interval_min;
	}
	public double getInterval_max() {
		return interval_max;
	}

	public Road copy() {
		Road copy = new Road(pos.getX(), pos.getY(), line_number, rotation, speed, interval_min, interval_max);
		return copy;
	}

	@Override
	public String toString() {
		return "Road [pos=" + pos + ", line_number=" + line_number + ", rotation=" + rotation + ", speed=" + speed
				+ "]";
	}	
}
