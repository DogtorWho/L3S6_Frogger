package objects.elements.linears.water;

import com.badlogic.gdx.graphics.Texture;

import textures.TextureFactory;

public class Turtle extends WaterElement {

	public Turtle(double x, double y, WaterStream line) {
		super(x, y, line);
	}
	
	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureTurtle();
	}
}
