package objects.elements;

import com.badlogic.gdx.graphics.Texture;

import textures.TextureFactory;

public class Explosion extends GameElement {
	private double time;
	
	public Explosion(double x, double y, double rota) {
		super(x, y, rota);
		
		time = 0.5f;
	}
	
	public void update(float delta) {
		time -= delta;
	}

	public double getTime() {
		return time;
	}

	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureExplosion();
	}
}
