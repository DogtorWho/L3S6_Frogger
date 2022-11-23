package renderer;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import controller.WorldController;
import objects.elements.Explosion;
import objects.elements.FlyLinear;
import objects.elements.Frogger;
import objects.elements.GameElement;
import objects.elements.linears.Tongue;
import objects.elements.linears.vehicules.Vehicule;
import objects.elements.linears.water.WaterElement;
import textures.TextureFactory;

public class WorldRenderer {
	private WorldController controller;
	private SpriteBatch batch;
	
	private Texture level_up;
	private float fade;	
	
	private Texture score;
	private BitmapFont font;
	private GlyphLayout score_number;
	
	private ShapeRenderer up;
	private ShapeRenderer down;
	private ShapeRenderer left;
	private ShapeRenderer right;
	private ShapeRenderer grid;
	
	private int anim_cpt;
	private int max_frames;
	private double frogger_elapsedTime;
	private float frame_length;
	
	public WorldRenderer(WorldController c) {
		controller = c;
		batch = new SpriteBatch();
		
		level_up = new Texture("texts/level_up.png");
		fade = 1.f;
		
		score = new Texture("texts/score.png");	
		font = new BitmapFont();
		font.setColor(Color.CHARTREUSE);
		font.getData().setScale(2, 2);
		
		score_number = new GlyphLayout();
		score_number.setText(font, "SCORE: 0");
		
		up = new ShapeRenderer();
		up.setAutoShapeType(true);
		up.setColor(Color.GREEN);
		
		down = new ShapeRenderer();
		down.setAutoShapeType(true);
		down.setColor(Color.RED);
		
		left = new ShapeRenderer();
		left.setAutoShapeType(true);
		left.setColor(Color.BLUE);
		
		right = new ShapeRenderer();
		right.setAutoShapeType(true);
		right.setColor(Color.YELLOW);
		
		grid = new ShapeRenderer();
		grid.setAutoShapeType(true);
		grid.setColor(Color.WHITE);
		
		anim_cpt = 0;
		max_frames = TextureFactory.getInstance().getFroggerAnim().size();
		frogger_elapsedTime = 0;
		frame_length = 0.075f;
	}
	
	public void render(float delta) {
		controller.update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		// draw the background
		batch.draw(TextureFactory.getInstance().getTextureBackground(), 0, 0);
		
		// draw all the water elements of the world
		for(ArrayList<WaterElement> line : controller.getWorld().getElementsWater()) {
			for(WaterElement e : line) {
				batch.draw(e.getTexture(), (float)e.getPos().getX(), (float)e.getPos().getY(), 
						(float)(e.getTexture().getWidth() / 2.0), (float)(e.getTexture().getHeight() / 2.0), 
						e.getTexture().getWidth(), e.getTexture().getHeight(), 
						1, 1, (float)e.getRotation(), 0, 0,
						(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
						false, false);
			}		
		}
		
		// draw all the elements of the world
		for(GameElement e : controller.getWorld().getElements()) {
			if(e instanceof Frogger && ((Frogger) e).isMoving()) { // we still in the animation
				int distance = 50;
				if(anim_cpt < max_frames) {
					Texture t = TextureFactory.getInstance().getFrameFrogger(anim_cpt);
					float x=0, y=0;
					if(e.isFacingUp()) {
						x = e.getHitbox().x - (distance - (distance/max_frames * anim_cpt));
						y = e.getHitbox().y - (distance - (distance/max_frames * anim_cpt));
					}
					else {
						x = e.getHitbox().x + (distance - (distance/max_frames * anim_cpt));
						y = e.getHitbox().y + (distance - (distance/max_frames * anim_cpt));
					}
					batch.draw(t, e.getHitbox().x, e.getHitbox().y,
							(t.getWidth() / 2.f), (t.getHeight() / 2.f), 
							e.getTexture().getWidth(), e.getTexture().getHeight(), 
							1, 1, (float)e.getRotation(), 0, 0,
							(int)t.getWidth(), (int)t.getHeight(),
							false, false);									
				}
				if(anim_cpt < max_frames-1) {
					anim_cpt++;
				}		
				else {
					anim_cpt = 0;
					((Frogger) e).setMoving(false);
				}			
			}
			else {			
				if(e.isFacingUp() || e.isFacingDown()) {
					batch.draw(e.getTexture(), e.getHitbox().x, e.getHitbox().y,
							(e.getTexture().getWidth() / 2.f), (e.getTexture().getHeight() / 2.f), 
							e.getTexture().getWidth(), e.getTexture().getHeight(), 
							1, 1, (float)e.getRotation(), 0, 0,
							(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
							false, false);
				}
				else {
					float d=0;
					if(e.getTexture().getWidth() > e.getTexture().getHeight())
						d = (e.getTexture().getWidth() / 2.f) - (e.getTexture().getHeight() / 2.f);
					else
						d = (e.getTexture().getHeight() / 2.f) - (e.getTexture().getWidth() / 2.f);
					
					batch.draw(e.getTexture(), e.getHitbox().x - d, e.getHitbox().y + d,
							(e.getTexture().getWidth() / 2.f), (e.getTexture().getHeight() / 2.f), 
							e.getTexture().getWidth(), e.getTexture().getHeight(), 
							1, 1, (float)e.getRotation(), 0, 0,
							(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
							false, false);
				}
			}		
		}
		
		// draw all the road elements of the world
		for(ArrayList<Vehicule> line : controller.getWorld().getElementsRoad()) {
			for(Vehicule e : line) {
				batch.draw(e.getTexture(), (float)e.getPos().getX(), (float)e.getPos().getY(), 
						(float)(e.getTexture().getWidth() / 2.0), (float)(e.getTexture().getHeight() / 2.0), 
						e.getTexture().getWidth(), e.getTexture().getHeight(), 
						1, 1, (float)e.getRotation(), 0, 0,
						(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
						false, false);
			}		
		}
		
		// draw all the shot elements of the world	
		for(Tongue e : controller.getWorld().getShots()) {
			if(e.isFacingUp() || e.isFacingDown()) {
				batch.draw(e.getTexture(), e.getHitbox().x, e.getHitbox().y,
						(e.getTexture().getWidth() / 2.f), (e.getTexture().getHeight() / 2.f), 
						e.getTexture().getWidth(), e.getTexture().getHeight(), 
						1, 1, (float)e.getRotation(), 0, 0,
						(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
						false, false);
			}
			else {
				float d = (e.getTexture().getHeight() / 2.f) - (e.getTexture().getWidth() / 2.f);		
				batch.draw(e.getTexture(), e.getHitbox().x + d, e.getHitbox().y - d,
						(e.getTexture().getWidth() / 2.f), (e.getTexture().getHeight() / 2.f), 
						e.getTexture().getWidth(), e.getTexture().getHeight(), 
						1, 1, (float)e.getRotation(), 0, 0,
						(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
						false, false);
			}
		}	
		
		// draw all the flies elements of the world	
		for(FlyLinear e : controller.getWorld().getFliesLinear()) {
			batch.draw(e.getTexture(), e.getHitbox().x, e.getHitbox().y,
					(e.getTexture().getWidth() / 2.f), (e.getTexture().getHeight() / 2.f), 
					e.getTexture().getWidth(), e.getTexture().getHeight(), 
					1, 1, (float)e.getRotation(), 0, 0,
					(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
					false, false);
		}	
			
		// draw all the explosions of the flies	
		for(Explosion e : controller.getWorld().getExplosions()) {
			batch.draw(e.getTexture(), e.getHitbox().x, e.getHitbox().y,
					(e.getTexture().getWidth() / 2.f), (e.getTexture().getHeight() / 2.f), 
					e.getTexture().getWidth(), e.getTexture().getHeight(), 
					1, 1, (float)e.getRotation(), 0, 0,
					(int)e.getTexture().getWidth(), (int)e.getTexture().getHeight(),
					false, false);
		}
		
					
		// draw the score
		batch.draw(score, 10, Gdx.graphics.getHeight() - score.getHeight() - 10);
		
		// draw the text
		score_number.setText(font, Integer.toString((int)controller.getScore()));
		font.draw(batch, score_number, score.getWidth() + 20, Gdx.graphics.getHeight() - 10);
		
		float x_life = Gdx.graphics.getWidth() - TextureFactory.getInstance().getTextureLife().getWidth() - 10;
		float y_life = Gdx.graphics.getHeight() - TextureFactory.getInstance().getTextureLife().getHeight() - 10;	
		for(int i=0; i < controller.getLife(); i++) {
			batch.draw(TextureFactory.getInstance().getTextureLife(), x_life, y_life);
			x_life -= (TextureFactory.getInstance().getTextureLife().getWidth() - 10);
		}		
		
		// draw the victory message
		if(controller.isVictory()) {
			fade -= delta;			
			if(fade < 0.f)
				fade = 0.f;

			batch.setColor(1.f, 1.f, 1.f, fade);
			batch.draw(level_up, Gdx.graphics.getWidth()/2 - level_up.getWidth()/2, Gdx.graphics.getHeight()/2);
			batch.setColor(1.f, 1.f, 1.f, 1.f);
		}
		else {
			fade = 1.f;
		}
		
		batch.end();

		show_debugGrid();
		show_hitboxes();
	}
	
	private void show_debugGrid() {
		up.begin(ShapeRenderer.ShapeType.Line);
		down.begin(ShapeRenderer.ShapeType.Line);
		left.begin(ShapeRenderer.ShapeType.Line);
		right.begin(ShapeRenderer.ShapeType.Line);
		grid.begin(ShapeRenderer.ShapeType.Line);
				
		// draw debug grid
		int size = 50;
		for(int i=0; i<12; i++) {
			for(int j=0; j<14; j++) {
				grid.rect(i*size, j*size, size, size);
			}
		}		
		
		// draw debug Frogger grid
		float x = (float)controller.getWorld().getFrogger().getHitbox().x;
		float y = (float)controller.getWorld().getFrogger().getHitbox().y;
		float w = (float)controller.getWorld().getFrogger().getHitbox().width;
		float h = (float)controller.getWorld().getFrogger().getHitbox().height;
		up.rect(x, y + h, w, h);
		down.rect(x, y - h, w , h);		
		left.rect(x - w, y, w, h);		
		right.rect(x + w, y, w, h);
		
		grid.end();
		right.end();
		left.end();
		down.end();
		up.end();	
	}
	
	private void show_hitboxes() {
		ShapeRenderer hitbox = new ShapeRenderer();
		hitbox.setAutoShapeType(true);
		hitbox.begin(ShapeRenderer.ShapeType.Line);
		
		for(ArrayList<WaterElement> line : controller.getWorld().getElementsWater()) {
			for(WaterElement e : line) {
				hitbox.setColor(Color.BLUE);
				hitbox.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
			}		
		}
		
		for(GameElement e : controller.getWorld().getElements()) {
			hitbox.setColor(Color.BLACK);
			hitbox.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
		}
		
		for(GameElement e : controller.getWorld().getFliesLinear()) {
			hitbox.setColor(Color.BLACK);
			hitbox.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
		}
		
		for(ArrayList<Vehicule> line : controller.getWorld().getElementsRoad()) {
			for(Vehicule e : line) {
				hitbox.setColor(Color.BROWN);
				hitbox.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
			}		
		}
				
		for(Tongue e : controller.getWorld().getShots()) {
			hitbox.setColor(Color.RED);
			hitbox.rect(e.getHitbox().x, e.getHitbox().y, e.getHitbox().width, e.getHitbox().height);
		}
		
		hitbox.end();
	}
	
	public void dispose( ) {
		batch.dispose();
	}
}
