package view;

import com.badlogic.gdx.Screen;

import game.FroggerGame;
import renderer.WorldRenderer;
import textures.TextureFactory;

public class FroggerScreen implements Screen {
	public TextureFactory textureFactory;
	
	private FroggerGame game;
	private WorldRenderer renderer;
	
	public FroggerScreen(FroggerGame game) {
		this.game = game;
		renderer = new WorldRenderer(game.getController());
	}
	
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		if(game.getController().getLife() <= 0) {
			game.setScreen(new EndScreen(game));
		}
		
		renderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		renderer.dispose();
	}

}
