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

import game.FroggerGame;
import textures.TextureFactory;

public class StartScreen implements Screen {	
	private FroggerGame game;
	
	private Texture title;
	private SpriteBatch batch;
	
	private BitmapFont font;
	private GlyphLayout start_text;
	
	private boolean fade_out;
	private float fade;	
	
	private double wait;
	private double wait_time;

	public StartScreen(FroggerGame game) {
		this.game = game;
		
		font = new BitmapFont();
		font.setColor(Color.CHARTREUSE);
		font.getData().setScale(5, 5);
		
		start_text = new GlyphLayout();
		start_text.setText(font, "Press any button");
		
		wait = 0;
		wait_time = 0.5;
		
		fade_out = true;
		fade = 1;
	}
	
	@Override
	public void show() {
		title = new Texture("texts/title.png");
		batch = new SpriteBatch();
		
		wait = 0;
	}

	@Override
	public void render(float delta) {
		changeScreen(delta);
		
		if(fade_out)
			fade -= delta/2;
		else
			fade += delta/2;
		
		if(fade < 0.3f) {
			fade_out = false;
			fade = 0.3f;
		}	
		else if(fade > 1.f) {
			fade_out = true;
			fade = 1.f;
		}
			
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		// draw the background
		batch.draw(TextureFactory.getInstance().getTextureBackground(), 0, 0);
		
		// draw the title
		batch.setColor(1.f, 1.f, 1.f, fade);
		batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, Gdx.graphics.getHeight()/2 + title.getHeight()/2);
		batch.setColor(1.f, 1.f, 1.f, 1.f);
		
		// draw the text
		font.draw(batch, start_text, Gdx.graphics.getWidth()/2 - start_text.width/2, 250);
		
		batch.end();
	}
	
	private void changeScreen(float delta) {
		wait += delta;
		
		if(wait > wait_time && (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ANY_KEY)) ) {
			game.setScreen(new FroggerScreen(game));
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
