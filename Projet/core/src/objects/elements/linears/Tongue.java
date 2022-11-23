package objects.elements.linears;

import com.badlogic.gdx.graphics.Texture;

import textures.TextureFactory;

public class Tongue extends GameElementLinear {
	private double air_time;
	private double speed;
	
	public Tongue(double x, double y, double rota, double time, double spd) {
		super(x, y, rota);
		air_time = time;
		speed = spd;
		
		// rotation for a texture which is saved with the face looking down
		faceUp = 180;
		faceDown = 0;
		faceLeft = 270;
		faceRight = 90;
		
		if(isFacingLeft() || isFacingRight()) {
			hitbox.width = getTexture().getHeight();
			hitbox.height = getTexture().getWidth();
		}
	}
	
	public void move(float delta) {	
		if(isFacingUp()) {
			pos.setY( pos.getY() + (delta * speed) );
			hitbox.y = (float) pos.getY();
		}		
		else if(isFacingDown()) {
			pos.setY( pos.getY() - (delta * speed) );
			hitbox.y = (float) pos.getY();
		}		
		else if(isFacingLeft()) {
			pos.setX( pos.getX() - (delta * speed) );
			hitbox.x = (float) pos.getX();
		}			
		else if(isFacingRight()) {
			pos.setX( pos.getX() + (delta * speed) );
			hitbox.x = (float) pos.getX();
		}	
	}
	
	public double getAir_time() {
		return air_time;
	}
	
	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureTongue();
	}
	
	@Override
	public String toString() {
		return "Tongue [position=" + pos + "]";
	}
}
