package objects.elements.linears.water;

import objects.elements.linears.GameElementLinear;

public class WaterElement extends GameElementLinear {
	private WaterStream water_line;
	
	public WaterElement(double x, double y, WaterStream line) {
		super(x, y, line.getRotation());
		water_line = line.copy(); // we don't want the reference to line
		move(water_line.getPos());
	}	
	
	public void move(float delta) {
		pos.setX( pos.getX() + (delta * water_line.getSpeed()) );
		hitbox.x = (float) pos.getX();
	}
	
	public WaterStream getWater_line() {
		return water_line;
	}

	public void setWater_line(WaterStream water_line) {
		this.water_line = water_line;
	}
	
	public double getRotation() {
		return water_line.getRotation();
	}
}
