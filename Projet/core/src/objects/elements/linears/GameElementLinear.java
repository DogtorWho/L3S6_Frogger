package objects.elements.linears;

import objects.elements.GameElement;

public abstract class GameElementLinear extends GameElement {

	public GameElementLinear(double x, double y, double rota) {
		super(x, y, rota);
		
		// rotation for a texture which is saved with the face looking right
		faceUp = 90;
		faceDown = 270;
		faceLeft = 180;
		faceRight = 0;
	}
	
	public void move(float delta) {
		if(isFacingUp()) {
			pos.setY(pos.getY() + delta);
			hitbox.y = (float) pos.getY();
		}		
		else if(isFacingDown()) {
			pos.setY(pos.getY() - delta);
			hitbox.y = (float) pos.getY();
		}		
		else if(isFacingLeft()) {
			pos.setX(pos.getX() - delta);
			hitbox.x = (float) pos.getX();
		}			
		else if(isFacingRight()) {
			pos.setX(pos.getX() + delta);
			hitbox.x = (float) pos.getX();
		}		
	}
}
