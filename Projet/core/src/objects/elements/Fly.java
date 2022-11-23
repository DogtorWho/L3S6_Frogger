package objects.elements;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;

import textures.TextureFactory;
import utils.Vector2f;

public class Fly extends GameElement {
	private ArrayList<Vector2f> possible_positions;
	private int current_position;
	
	public Fly(double x, double y, double rota) {
		super(x, y, rota);
		possible_positions = new ArrayList<Vector2f>();
		current_position = 0;
	}
	
	public void changePosition() {
		int i; 
		do { // the fly must change position
			i = (int)(Math.random() * possible_positions.size());	
		} while (i == current_position);
		 
		this.move(possible_positions.get(i));
		current_position = i;
	}
	
	public void addPosition(int x, int y) {
		possible_positions.add(new Vector2f(x, y));
	}	
	public void addPosition(Vector2f v) {
		possible_positions.add(v);
	}
	
	public ArrayList<Vector2f> getPossible_positions() {
		return possible_positions;
	}
	public void setPossible_positions(ArrayList<Vector2f> possible_positions) {
		this.possible_positions = possible_positions;
	}

	public Texture getTexture() {
		return TextureFactory.getInstance().getTextureFly();
	}
}
