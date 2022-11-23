package objects.elements.linears.vehicules;

import com.badlogic.gdx.graphics.Texture;

import objects.elements.linears.GameElementLinear;
import textures.TextureFactory;

public class Vehicule extends GameElementLinear {
	private Road road_line;
	
	public Vehicule(double x, double y, Road line) {
		super(x, y, line.getRotation());
		road_line = line.copy(); // we don't want the reference to line
		move(road_line.getPos());
	}
	
	public void move(float delta) {
		pos.setX( pos.getX() + (delta * road_line.getSpeed()) );
		hitbox.x = (float) pos.getX();
	}
	
	public Road getRoad_line() {
		return road_line;
	}

	public void setRoad_line(Road road_line) {
		this.road_line = road_line;
	}
	
	public double getRotation() {
		return road_line.getRotation();
	}
	
	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureVehicule();
	}
}
