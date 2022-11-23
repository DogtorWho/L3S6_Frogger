package game;

import com.badlogic.gdx.Game;

import controller.WorldController;
import view.StartScreen;

public class FroggerGame extends Game {
	private WorldController controller; // controller
	
	@Override
	public void create() {
		controller = new WorldController();
		
		setScreen(new StartScreen(this));
	}
	
	@Override
	public void render() {		
		super.render();
	}

	public WorldController getController() {
		return controller;
	}
	public void setController(WorldController controller) {
		this.controller = controller;
	}
}
