package objects.elements.linears.vehicules.trucks;

import com.badlogic.gdx.graphics.Texture;

import objects.elements.linears.vehicules.Road;
import objects.elements.linears.vehicules.Vehicule;
import textures.TextureFactory;

public class BrownTruck extends Vehicule {
	
	public BrownTruck(double x, double y, Road line) {
		super(x, y, line);
	}

	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureBrownTruck();
	}
}
