package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import controller.WorldController;
import game.FroggerGame;
import textures.TextureFactory;

public class EndScreen implements Screen {	
	private FroggerGame game;
	
	private Texture game_over;
	private SpriteBatch batch;
	
	private BitmapFont font;
	private GlyphLayout score_text;
	
	private double wait;
	private double wait_time;	

	public EndScreen(FroggerGame game) {
		this.game = game;
		
		game_over = new Texture("texts/game_over_2.png");
		batch = new SpriteBatch();
		
		font = new BitmapFont();
		font.setColor(Color.CHARTREUSE);
		font.getData().setScale(5, 5);

		score_text = new GlyphLayout();
		String score = "SCORE: " + (int)game.getController().getScore();
		score_text.setText(font, score);
		
		wait = 0;
		wait_time = 0.5;	
	}
	
	@Override
	public void show() {
		wait = 0;
	}

	@Override
	public void render(float delta) {
		changeScreen(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		// draw the background
		batch.draw(TextureFactory.getInstance().getTextureBackground(), 0, 0);
				
		// draw the game over
		batch.draw(game_over, Gdx.graphics.getWidth()/2 - game_over.getWidth()/2, Gdx.graphics.getHeight()/2 - 35);
		
		// draw the text
		font.draw(batch, score_text, Gdx.graphics.getWidth()/2 - score_text.width/2, 200);
		
		batch.end();
	}
	
	private void changeScreen(float delta) {
		wait += delta;
		
		if(wait > wait_time && (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ANY_KEY)) ) {
			game.setController(new WorldController());
			game.setScreen(new StartScreen(game));
		}	
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
		batch.dispose();
	}
}
