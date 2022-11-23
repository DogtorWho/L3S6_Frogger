package objects.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import textures.TextureFactory;
import utils.Vector2f;

public class Frogger extends GameElement {		
	private int distance;
	private int current_frame;
	private int max_frame;
	
	private boolean moving;
	
	public Frogger(double x, double y, double rota) {
		super(x, y, rota);
		initialPos = new Vector2f(x, y);
		
		distance = 50;
		current_frame = 0;
		max_frame = TextureFactory.getInstance().getFroggerAnim().size();
		moving = false;
	}
	
	public void updateFrame() {
		if(current_frame >= max_frame-1)
			current_frame = 0;
		else
			current_frame++;		
	}
	
	public void resetPosition() {
		current_frame = 0;
		pos.setVector2f(initialPos);
		hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getWidth(), getTexture().getHeight());
		rotation = faceUp;		
	}
	
	public void moveUp() {
		//updateFrame();
		moving = true;
		if(hitbox.y + (hitbox.height + distance) <= Gdx.graphics.getHeight()) {
			move(getPos().getX(), getPos().getY() + distance);	
		}	
	}
	public void moveDown() {
		//updateFrame();
		moving = true;
		if(hitbox.y - distance >= 0) {
			move(getPos().getX(), getPos().getY() - distance);	
		}	
	}
	public void moveLeft() {
		//updateFrame();
		moving = true;
		if(hitbox.x - distance >= 0) {
			move(getPos().getX() - distance, getPos().getY());
		}	
	}
	public void moveRight() {	
		//updateFrame();
		moving = true;
		if(hitbox.x + (hitbox.width + distance) <= Gdx.graphics.getWidth()) {
			move(getPos().getX() + distance, getPos().getY());		
		}	
	}
	
	
	
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Texture getTexture() {
		//return TextureFactory.getInstance().getTextureFrogger();
		return TextureFactory.getInstance().getFrameFrogger(current_frame);
	}

	@Override
	public String toString() {
		return "Frogger [position=" + pos + "]";
	}
}
