package objects.elements.linears.vehicules.cars;

import com.badlogic.gdx.graphics.Texture;

import objects.elements.linears.vehicules.Road;
import objects.elements.linears.vehicules.Vehicule;
import textures.TextureFactory;

public class BlueCar extends Vehicule {
	public BlueCar(double x, double y, Road line) {
		super(x, y, line);
	}

	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureBlueCar();
	}
}
