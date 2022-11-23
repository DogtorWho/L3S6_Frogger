package objects.elements.linears.vehicules.trucks;

import com.badlogic.gdx.graphics.Texture;

import objects.elements.linears.vehicules.Road;
import objects.elements.linears.vehicules.Vehicule;
import textures.TextureFactory;

public class RedTruck extends Vehicule {
	public RedTruck(double x, double y, Road line) {
		super(x, y, line);
	}

	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureRedTruck();
	}
}
