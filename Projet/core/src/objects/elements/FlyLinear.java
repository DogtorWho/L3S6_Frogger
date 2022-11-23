package objects.elements;

import com.badlogic.gdx.graphics.Texture;

import textures.TextureFactory;
import utils.Vector2f;

public class FlyLinear extends GameElement {
	// A is the pos of GameElement
	private Vector2f A;
	private Vector2f B;
	private Vector2f AB;
	private double speed;
	private double t;
	
	public FlyLinear(double x, double y, double rota, double x_b, double y_b, double spd) {
		super(x, y, rota);
		
		A = new Vector2f(x, y);
		B = new Vector2f(x_b, y_b);
		AB = new Vector2f(B.getX() - pos.getX(), B.getY() - pos.getY());
		t = 0;
		
		speed = spd;
	}

	public void move(float delta) {
		if(t <= 1.f)
			t += delta/10;
		else
			t = 1;
		
		move(A.getX() + (AB.getX() * t), A.getY() + (AB.getY() * t));
	}

	public Vector2f getB() {
		return B;
	}
	public Vector2f getAB() {
		return AB;
	}
	
	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureFly();
	}
}
