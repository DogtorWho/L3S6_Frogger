package objects.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import textures.TextureFactory;
import utils.Vector2f;

public abstract class GameElement {
	protected Vector2f initialPos;
	protected Vector2f pos;
	protected double rotation;
	protected Rectangle hitbox;
	
	protected double faceUp;
	protected double faceDown;
	protected double faceLeft;
	protected double faceRight;
	
	public GameElement(double x, double y, double rota) {
		initialPos = new Vector2f(x, y);
		pos = new Vector2f(x, y);			
		rotation = rota;
		hitbox = new Rectangle((float)x, (float)y, getTexture().getWidth(), getTexture().getHeight());
		
		// rotation for a texture which is saved with the face looking down
		faceUp = 180;
		faceDown = 0;
		faceLeft = 270;
		faceRight = 90;
	}
	
	public void move(double x, double y) {
		pos.setVector2f(x, y);
		
		hitbox.x = (float)x;
		hitbox.y = (float)y;
	}
	public void move(Vector2f v) {
		pos = v;
		
		hitbox.x = (float)v.getX();
		hitbox.y = (float)v.getY();
	}
	
	public Vector2f getInitialPos() {
		return initialPos;
	}	
	public void resetPosition() {
		pos.setVector2f(initialPos);
		hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getWidth(), getTexture().getHeight());
		rotation = faceUp;
	}
	
	public Vector2f getPos() {
		return pos;
	}
	public void setPos(Vector2f position) {
		this.pos = position;
		
		hitbox.x = (float)position.getX();
		hitbox.y = (float)position.getY();
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public double getRotation() {
		return rotation;
	}
	public void setRotation(double rota) {
		this.rotation = rota;
	}
	
	public double getFaceUp() {
		return faceUp;
	}
	public double getFaceDown() {
		return faceDown;
	}
	public double getFaceLeft() {
		return faceLeft;
	}
	public double getFaceRight() {
		return faceRight;
	}
	
	public void turnUp() {
		if(isFacingLeft() || isFacingRight()) { // update the position and hitbox
			if(getTexture().getWidth() > getTexture().getHeight()) {
				float delta = (getTexture().getWidth() / 2.f) - (getTexture().getHeight() / 2.f);
				pos.setX(pos.getX() - delta);
				pos.setY(pos.getY() + delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getWidth(), getTexture().getHeight());
			}
			else { // height >= width
				System.out.println("OSKOUR");
				float delta = (getTexture().getHeight() / 2.f) - (getTexture().getWidth() / 2.f);
				pos.setX(pos.getX() - delta);
				pos.setY(pos.getY() + delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getWidth(), getTexture().getHeight());
			}
		}
				
		rotation = faceUp;
	}
	public void turnDown() {
		if(isFacingLeft() || isFacingRight()) { // update the position and hitbox
			if(getTexture().getWidth() > getTexture().getHeight()) {
				float delta = (getTexture().getWidth() / 2.f) - (getTexture().getHeight() / 2.f);
				pos.setX(pos.getX() - delta);
				pos.setY(pos.getY() + delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getWidth(), getTexture().getHeight());
			}
			else { // height >= width
				System.out.println("OSKOUR");
				float delta = (getTexture().getHeight() / 2.f) - (getTexture().getWidth() / 2.f);
				pos.setX(pos.getX() - delta);
				pos.setY(pos.getY() + delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getWidth(), getTexture().getHeight());
			}
		}
		
		rotation = faceDown;
	}
	public void turnLeft() {
		if(isFacingUp() || isFacingDown()) { // update the position and hitbox
			if(getTexture().getWidth() > getTexture().getHeight()) {
				float delta = (getTexture().getWidth() / 2.f) - (getTexture().getHeight() / 2.f);
				pos.setX(pos.getX() + delta);
				pos.setY(pos.getY() - delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getHeight(), getTexture().getWidth());
			}
			else { // height >= width
				System.out.println("OSKOUR");
				float delta = (getTexture().getHeight() / 2.f) - (getTexture().getWidth() / 2.f);
				pos.setX(pos.getX() - delta);
				pos.setY(pos.getY() + delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getHeight(), getTexture().getWidth());
			}
		}
		
		rotation = faceLeft;
	}
	public void turnRight() {
		if(isFacingUp() || isFacingDown()) { // update the position and hitbox
			if(getTexture().getWidth() > getTexture().getHeight()) {
				float delta = (getTexture().getWidth() / 2.f) - (getTexture().getHeight() / 2.f);
				pos.setX(pos.getX() + delta);
				pos.setY(pos.getY() - delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getHeight(), getTexture().getWidth());
			}
			else { // height >= width
				System.out.println("OSKOUR");
				float delta = (getTexture().getHeight() / 2.f) - (getTexture().getWidth() / 2.f);
				pos.setX(pos.getX() - delta);
				pos.setY(pos.getY() + delta);
				hitbox.set((float)pos.getX(), (float)pos.getY(), getTexture().getHeight(), getTexture().getWidth());
			}
		}
		
		rotation = faceRight;
	}
	
	public boolean isFacingUp() {
		if(rotation == faceUp)
			return true;
		return false;
	}
	public boolean isFacingDown() {
		if(rotation == faceDown)
			return true;
		return false;
	}
	public boolean isFacingLeft() {
		if(rotation == faceLeft)
			return true;
		return false;
	}
	public boolean isFacingRight() {
		if(rotation == faceRight)
			return true;
		return false;
	}

	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureDefault();
	}

	@Override
	public String toString() {
		return "GameElement [pos=" + pos + "]";
	}
}
