package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import model.FroggerWorld;
import objects.elements.linears.water.Turtle;
import objects.elements.linears.water.WaterElement;
import objects.elements.linears.water.WaterStream;
import objects.elements.linears.water.logs.LargeLog;
import objects.elements.linears.water.logs.MediumLog;
import objects.elements.linears.water.logs.SmallLog;
import textures.TextureFactory;
import utils.Vector2f;
import objects.elements.Explosion;
import objects.elements.Fly;
import objects.elements.FlyLinear;
import objects.elements.GameElement;
import objects.elements.linears.Tongue;
import objects.elements.linears.vehicules.Road;
import objects.elements.linears.vehicules.Vehicule;
import objects.elements.linears.vehicules.cars.BlueCar;
import objects.elements.linears.vehicules.cars.GreenCar;
import objects.elements.linears.vehicules.cars.YellowCar;
import objects.elements.linears.vehicules.trucks.BrownTruck;
import objects.elements.linears.vehicules.trucks.RedTruck;

public class WorldController {
	private FroggerWorld world;
	
	private double potential_score;
	private double score;
	private int life;
	
	private double timer_fly;
	private double maxTimer_fly;
	
	private double timer_frogger;
	private double frogger_cooldown;
	
	private double timer_shot;
	private double shot_cooldown;
	
	private boolean victory;
	private double timer_victory;
	
	private double timer_respawn;
	
	private double timer_flies;
	
	private double wait;
	private double wait_time;
	
	private boolean keyUpPressed;
	private boolean keyDownPressed;
	private boolean keyLeftPressed;
	private boolean keyRightPressed;
	private boolean mousePressed;
	private boolean keyShotPressed;
	private boolean keyDebugPressed;
	
	private ArrayList<Rectangle> winning_places;
	
	private boolean fly_eaten;
	
	private boolean debug_mode;
	
	private enum Direction {UP, DOWN, LEFT, RIGHT}
	
	
	public WorldController() {
		world = new FroggerWorld();
		world.init("init/init.xml"); // create all the objects
		
		potential_score = 0;
		score = 0;
		life = world.getFactory().getLife_number();
		
		timer_fly = 0;
		maxTimer_fly = world.getFactory().getTimer_fly();
		
		timer_frogger = 0;
		frogger_cooldown = world.getFactory().getFrogger_cooldown();
		
		timer_shot = 0;
		shot_cooldown = world.getFactory().getShot_cooldown();
		
		victory = false;
		timer_victory = 0;
		
		timer_respawn = 0;
		
		timer_flies = 0;
		
		wait = 0;
		wait_time = 0.5;
		
		keyUpPressed = false;
		keyDownPressed = false;
		keyLeftPressed = false;
		keyRightPressed = false;
		mousePressed = false;
		keyShotPressed = false;
		keyDebugPressed = false;
		
		winning_places = new ArrayList<Rectangle>();
		float w = world.getFly().getTexture().getWidth();
		float h = world.getFly().getTexture().getHeight();
		for(Vector2f pos : world.getFly().getPossible_positions()) 
			winning_places.add(new Rectangle((float)pos.getX(), (float)pos.getY(), w, h));
		
		fly_eaten = false;
		
		debug_mode = false;
	}
	
	public void update(float delta) {
		if(victory && timer_victory < 1) {
			timer_victory += delta;
		}
		else {
			victory = false;
			timer_victory = 0;
		}
		
		if(!victory) {
			collision_update(delta);
			score_update(delta);	
			fly_update(delta);	
			shot_update(delta);
			fliesLinear_update(delta);
			if(wait < wait_time) {
				wait += delta;
			}
			else {
				frogger_update(delta);
				frogger_shoot(delta);
			}	
			elementsLinear_update(delta);
		}
	}
	
	private void collision_update(float delta) {	
		if(!debug_mode) {
			Rectangle frogger_hitbox = world.getFrogger().getHitbox();
			Rectangle fly_hitbox = world.getFly().getHitbox();
			
			for(Rectangle r : winning_places) {
				/*if(Intersector.overlaps(frogger_hitbox, r) && !Intersector.overlaps(frogger_hitbox, fly_hitbox)) {
					levelUp();	
				}*/
				if(Intersector.overlaps(frogger_hitbox, r)) {
					if(Intersector.overlaps(frogger_hitbox, fly_hitbox)) {	
						Iterator<GameElement> itr = world.getElements().iterator();
					    while (itr.hasNext()) {
					    	GameElement e = itr.next();	
								
							if(e instanceof Fly)
								itr.remove();			
					    }
						fly_eaten = true;
					}
					levelUp();	
				}
			}	
			
			// check for collisions on the road
			if(frogger_hitbox.y > 50 && frogger_hitbox.y < 300) {
				for(ArrayList<Vehicule> line : world.getElementsRoad()) {
					for(Vehicule e : line) {
						if(Intersector.overlaps(frogger_hitbox, e.getHitbox())) {
							life--;
							if(life > 0) {
								world.getFrogger().resetPosition();
								// wait a bit before moving
								wait = 0;
								wait_time = 0.5;
							}
						}				
					}
				}
			}		
			
			// check for collision in the water
			if(frogger_hitbox.y >= 350 && frogger_hitbox.y < 600) {
				boolean safe = false;
				for(ArrayList<WaterElement> line : world.getElementsWater()) {
					for(WaterElement e : line) {
						if(Intersector.overlaps(frogger_hitbox, e.getHitbox())) {
							safe = true;
							if(frogger_hitbox.x > 0 && frogger_hitbox.x + frogger_hitbox.width < Gdx.graphics.getWidth())
								world.getFrogger().move(frogger_hitbox.x + (delta * e.getWater_line().getSpeed()), frogger_hitbox.y);
							break;		
						}				
					}
				}
				if(!safe) {
					life--;
					if(life > 0) {
						world.getFrogger().resetPosition();
						// wait a bit before moving
						wait = 0;
						wait_time = 0.5;
					}
				}
			}	
		}
	}
	
	private void levelUp() {
		world.getFrogger().resetPosition();
		
		score += potential_score;
		potential_score = 0;
		
		victory = true;
		
		double percent = world.getFactory().getSpeed_percent() / 100;
		
		for(WaterStream stream : world.getFactory().getWater_lines())
			stream.setSpeed(stream.getSpeed() + (stream.getSpeed() * percent));
		
		for(ArrayList<WaterElement> line : world.getElementsWater())
			for(WaterElement e : line)
				e.getWater_line().setSpeed(e.getWater_line().getSpeed() + (e.getWater_line().getSpeed() * percent));
					
		for(Road road : world.getFactory().getRoad_lines())
			road.setSpeed(road.getSpeed() + (road.getSpeed() * percent));
		
		for(ArrayList<Vehicule> line : world.getElementsRoad())
			for(Vehicule e : line)
				e.getRoad_line().setSpeed(e.getRoad_line().getSpeed() + (e.getRoad_line().getSpeed() * percent));
		
		if(fly_eaten) {
			life++;
			
			Fly fly = new Fly(0, 0, 0);
			fly.setPossible_positions(world.getFactory().getFly_positions());
			fly.changePosition();
			world.getElements().add(fly);
			System.out.println("Controller:levelUp: new fly added");
			
			fly_eaten = false;
		}
	}
	
	private void score_update(float delta) {
		potential_score += delta;
	}
	
	private void fly_update(float delta) {
		timer_fly += delta;
		if(timer_fly > maxTimer_fly) { // every timer tick, the fly change its position
			world.getFly().changePosition();
			timer_fly = maxTimer_fly - timer_fly; // we want to keep the decimals	
		}
	}
	
	private void frogger_update(float delta) {
		keyboard_update(delta);	
		mouse_update(delta);	
	}
	
	private void move_frogger(Direction d) {
		Rectangle next_move = new Rectangle(world.getFrogger().getHitbox());
		Rectangle fly_hitbox =  new Rectangle(world.getFly().getHitbox());
		int distance = 50;
		
		if(d == Direction.UP) {
			next_move.setY(next_move.y + distance);
			if(next_move.getY() >= winning_places.get(0).y) {
				for(Rectangle r : winning_places) {
					/*if(Intersector.overlaps(next_move, r) && !Intersector.overlaps(next_move, fly_hitbox)) {
						world.getFrogger().moveUp();
						potential_score += 1;
						break;
					}*/
					if(Intersector.overlaps(next_move, r)) {
						world.getFrogger().moveUp();
						potential_score += 1;
						break;
					}
				}
				world.getFrogger().turnUp();			
			}
			else {
				if(!Intersector.overlaps(next_move, fly_hitbox)) {
					if(!world.getFrogger().isFacingUp()) {
						world.getFrogger().turnUp();
					}	
					else {
						world.getFrogger().moveUp();
						potential_score += 1;
					}				
				}
			}
		}
		else if(d == Direction.DOWN) {
			next_move.setY(next_move.y - distance);
			if(!Intersector.overlaps(next_move, fly_hitbox)) {
				if(!world.getFrogger().isFacingDown())
					world.getFrogger().turnDown();
				else
					world.getFrogger().moveDown();
			}			
		}
		else if(d == Direction.LEFT) {
			next_move.setX(next_move.x - distance);
			if(!Intersector.overlaps(next_move, fly_hitbox)) {
				if(!world.getFrogger().isFacingLeft())
					world.getFrogger().turnLeft();
				else
					world.getFrogger().moveLeft();
			}
		}
		else if(d == Direction.RIGHT) {
			next_move.setX(next_move.x + distance);
			if(!Intersector.overlaps(next_move, fly_hitbox)) {
				if(!world.getFrogger().isFacingRight())
					world.getFrogger().turnRight();
				else
					world.getFrogger().moveRight();
			}
		}	
	}
	
	private void keyboard_update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.D) && !keyDebugPressed) {
			if(debug_mode) {
				System.out.println("Normal Mode");
				debug_mode = false;
			}			
			else {
				System.out.println("Debug Mode");
				debug_mode = true;
			}	
			keyDebugPressed = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.D) && keyDebugPressed) {
			keyDebugPressed = false;
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP) && keyUpPressed) {
			timer_frogger += delta;
			if(timer_frogger > frogger_cooldown) {
				move_frogger(Direction.UP);
				timer_frogger = frogger_cooldown - timer_frogger;	
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.UP) && !keyUpPressed) {
			move_frogger(Direction.UP);
			timer_frogger = 0;
			keyUpPressed = true;
		}
		else if(!Gdx.input.isKeyPressed(Keys.UP) && keyUpPressed) {	
			keyUpPressed = false;
		}
		
					
		if(Gdx.input.isKeyPressed(Keys.DOWN) && keyDownPressed) {
			timer_frogger += delta;
			if(timer_frogger > frogger_cooldown) {
				move_frogger(Direction.DOWN);
				timer_frogger = frogger_cooldown - timer_frogger;	
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN) && !keyDownPressed) {
			move_frogger(Direction.DOWN);
			timer_frogger = 0;
			keyDownPressed = true;	
		}	
		else if(keyDownPressed && !Gdx.input.isKeyPressed(Keys.DOWN)) {
			keyDownPressed = false;
		}
		
			
		if(Gdx.input.isKeyPressed(Keys.LEFT) && keyLeftPressed) {
			timer_frogger += delta;
			if(timer_frogger > frogger_cooldown) {
				move_frogger(Direction.LEFT);
				timer_frogger = frogger_cooldown - timer_frogger;
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.LEFT) && !keyLeftPressed) {
			move_frogger(Direction.LEFT);
			timer_frogger = 0;
			keyLeftPressed = true;
		}	
		else if(keyLeftPressed && !Gdx.input.isKeyPressed(Keys.LEFT)) {
			keyLeftPressed = false;
		}
		
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT) && keyRightPressed) {
			timer_frogger += delta;
			if(timer_frogger > frogger_cooldown) {
				move_frogger(Direction.RIGHT);
				timer_frogger = frogger_cooldown - timer_frogger;
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT) && !keyRightPressed) {
			move_frogger(Direction.RIGHT);
			timer_frogger = 0;
			keyRightPressed = true;	
		}				
		else if(keyRightPressed && !Gdx.input.isKeyPressed(Keys.RIGHT)) {
			keyRightPressed = false;
		}
	}
	
	private void mouse_update(float delta) {
		if(Gdx.input.isTouched()) {
			double x = Gdx.input.getX();
			double y = Gdx.input.getY();
			
			double low_area = Gdx.graphics.getHeight() * 0.3f;
			double high_area = Gdx.graphics.getHeight() * 0.6f;
			double middle = Gdx.graphics.getHeight() / 2.f;
			
			if(mousePressed) {
				if(y < low_area) {
					timer_frogger += delta;
					if(timer_frogger > frogger_cooldown) {
						move_frogger(Direction.UP);
						timer_frogger = frogger_cooldown - timer_frogger;	
					}
				}
				else if(y >= high_area) {
					timer_frogger += delta;
					if(timer_frogger > frogger_cooldown) {
						move_frogger(Direction.DOWN);
						timer_frogger = frogger_cooldown - timer_frogger;	
					}
				}
				else if(x < middle && y >= low_area && y < high_area) {
					timer_frogger += delta;
					if(timer_frogger > frogger_cooldown) {
						move_frogger(Direction.LEFT);
						timer_frogger = frogger_cooldown - timer_frogger;	
					}
				}
				else if(x >= middle && y >= low_area && y < high_area) {
					timer_frogger += delta;
					if(timer_frogger > frogger_cooldown) {
						move_frogger(Direction.RIGHT);
						timer_frogger = frogger_cooldown - timer_frogger;	
					}
				}
			}
			else {
				if(y < low_area) {
					move_frogger(Direction.UP);
				}
				else if(y >= high_area) {
					move_frogger(Direction.DOWN);
				}
				else if(x < middle && y >= low_area && y < high_area) {
					move_frogger(Direction.LEFT);
				}
				else if(x >= middle && y >= low_area && y < high_area) {
					move_frogger(Direction.RIGHT);
				}		
				timer_frogger = 0;
				mousePressed = true;
			}
		}
		else {
			mousePressed = false;
		}	
	}

	private void elementsLinear_update(float delta) {			
	    for(ArrayList<WaterElement> line : world.getElementsWater()) {
	    	Iterator<WaterElement> itr = line.iterator();
		    while (itr.hasNext()) {
		    	WaterElement e = itr.next();
				
		    	e.move(delta); 		
					
				if( (e.getPos().getX() > Gdx.graphics.getWidth()) || (e.getPos().getX() + e.getTexture().getWidth() < 0) )
					itr.remove();			
		    }	
		}
	    
	    for(ArrayList<Vehicule> line : world.getElementsRoad()) {
	    	Iterator<Vehicule> itr = line.iterator();
		    while (itr.hasNext()) {
		    	Vehicule e = itr.next();
				
		    	e.move(delta);
					
				if( (e.getPos().getX() > Gdx.graphics.getWidth()) || (e.getPos().getX() + e.getTexture().getWidth() < 0) )
					itr.remove();								
		    }	
		}    
	    
	    timer_respawn += delta;
	        
	    if(timer_respawn > 1) { // delay to not spam the random function
	    	int random_interval = 0;
	    	for(ArrayList<WaterElement> line : world.getElementsWater()) {    
		    	if(line.size() > 0) {
		    		double min = line.get(0).getWater_line().getInterval_min();
		    		double max = line.get(0).getWater_line().getInterval_max();
		    		random_interval = (int)(((Math.random() * (max - min))) + min);
		    		
		    		if(line.get(0).getRotation() == 0) {
		    			if(line.get(0).getPos().getX() > random_interval)
		    				spawn_random_WaterElement(line);
		    		}
		    		else {
		    			if( (line.get(0).getPos().getX() + line.get(0).getTexture().getWidth()) < (Gdx.graphics.getWidth() - random_interval) )
		    				spawn_random_WaterElement(line);
		    		}	
		    	}		
		    }
	    	
	    	for(ArrayList<Vehicule> line : world.getElementsRoad()) {	
		    	if(line.size() > 0) {
		    		double min = line.get(0).getRoad_line().getInterval_min();
		    		double max = line.get(0).getRoad_line().getInterval_max();
		    		random_interval = (int)(((Math.random() * (max - min))) + min);
		    		
		    		if(line.get(0).getRotation() == 0) {
		    			if(line.get(0).getPos().getX() > random_interval)
		    				spawn_random_Vehicule(line);
		    		}
		    		else {
		    			if( (line.get(0).getPos().getX() + line.get(0).getTexture().getWidth()) < (Gdx.graphics.getWidth() - random_interval) )
		    				spawn_random_Vehicule(line);
		    		}	
		    	}		
		    }  
	    	
	    	timer_respawn = 0;
	    }   	    
	}
	
	private void spawn_random_WaterElement(ArrayList<WaterElement> line) {
		WaterStream stream = line.get(0).getWater_line().copy();

		switch((int)(Math.random() * 4)) {
			case 0 : 
				if(stream.getRotation() == 0)
					stream.setPos(-TextureFactory.getInstance().getTextureLargeLog().getWidth(), stream.getPos().getY());
				else
					stream.setPos(Gdx.graphics.getWidth(), stream.getPos().getY());
				
				line.add(0, new LargeLog(0, 0, stream));
			break;
			
			case 1 : 
				if(stream.getRotation() == 0)
					stream.setPos(-TextureFactory.getInstance().getTextureMediumLog().getWidth(), stream.getPos().getY());
				else
					stream.setPos(Gdx.graphics.getWidth(), stream.getPos().getY());
				
				line.add(0, new MediumLog(0, 0, stream));
			break;
			
			case 2 : 
				if(stream.getRotation() == 0)
					stream.setPos(-TextureFactory.getInstance().getTextureSmallLog().getWidth(), stream.getPos().getY());
				else
					stream.setPos(Gdx.graphics.getWidth(), stream.getPos().getY());
				
				line.add(0, new SmallLog(0, 0, stream));
			break;
			
			case 3 : 
				if(stream.getRotation() == 0)
					stream.setPos(-TextureFactory.getInstance().getTextureTurtle().getWidth(), stream.getPos().getY());
				else
					stream.setPos(Gdx.graphics.getWidth(), stream.getPos().getY());
			
				line.add(0, new Turtle(0, 0, stream));
			break;
		}
	}
	
	private void spawn_random_Vehicule(ArrayList<Vehicule> line) {
		Road road = line.get(0).getRoad_line().copy();

		switch((int)(Math.random() * 5)) {
			case 0 :
				if(road.getRotation() == 0)
					road.setPos(-TextureFactory.getInstance().getTextureBlueCar().getWidth(), road.getPos().getY());
				else
					road.setPos(Gdx.graphics.getWidth(), road.getPos().getY());
			
				line.add(0, new BlueCar(0, 0, road));
			break;
				
			case 1 :
				if(road.getRotation() == 0)
					road.setPos(-TextureFactory.getInstance().getTextureGreenCar().getWidth(), road.getPos().getY());
				else
					road.setPos(Gdx.graphics.getWidth(), road.getPos().getY());
			
				line.add(0, new GreenCar(0, 0, road));
			break;
			
			case 2 : 
				if(road.getRotation() == 0)
					road.setPos(-TextureFactory.getInstance().getTextureYellowCar().getWidth(), road.getPos().getY());
				else
					road.setPos(Gdx.graphics.getWidth(), road.getPos().getY());
			
				line.add(0, new YellowCar(0, 0, road));
			break;
			
			case 3 : 
				if(road.getRotation() == 0)
					road.setPos(-TextureFactory.getInstance().getTextureBrownTruck().getWidth(), road.getPos().getY());
				else
					road.setPos(Gdx.graphics.getWidth(), road.getPos().getY());
			
				line.add(0, new BrownTruck(0, 0, road));
			break;
			
			case 4 :
				if(road.getRotation() == 0)
					road.setPos(-TextureFactory.getInstance().getTextureRedTruck().getWidth(), road.getPos().getY());
				else
					road.setPos(Gdx.graphics.getWidth(), road.getPos().getY());
			
				line.add(0, new RedTruck(0, 0, road));
			break;
		}
	}
	
	private void frogger_shoot(float delta) {
		if(world.getShots().size() < world.getFactory().getShot_number()) {	
			if(Gdx.input.isKeyPressed(Keys.SPACE) && keyShotPressed) {
				timer_shot += delta;
				if(timer_shot > shot_cooldown) {
					world.getShots().add(create_shot());	
					timer_shot = shot_cooldown - timer_shot;
				}
			}
			else if(Gdx.input.isKeyPressed(Keys.SPACE) && !keyShotPressed) {
				world.getShots().add(create_shot());	
				timer_shot = 0;
				keyShotPressed = true;	
			}				
			else if(keyShotPressed && !Gdx.input.isKeyPressed(Keys.SPACE)) {
				keyShotPressed = false;
			}
		}
		else {
			timer_shot = shot_cooldown;
		}
	}
	
	private Tongue create_shot() {
		double rota = world.getFrogger().getRotation();
		double x=0, y=0;
		
		if(rota == world.getFrogger().getFaceUp()) {
			x = world.getFrogger().getHitbox().x + (world.getFrogger().getHitbox().width / 2.f) - (TextureFactory.getInstance().getTextureTongue().getWidth() / 2.f);
			y = world.getFrogger().getHitbox().y + world.getFrogger().getHitbox().height;
		}
		else if(rota == world.getFrogger().getFaceDown()) {
			x = world.getFrogger().getHitbox().x + (world.getFrogger().getHitbox().width / 2.f) - (TextureFactory.getInstance().getTextureTongue().getWidth() / 2.f);
			y = world.getFrogger().getHitbox().y - TextureFactory.getInstance().getTextureTongue().getHeight();
		}
		else if(rota == world.getFrogger().getFaceLeft()) {
			x = world.getFrogger().getHitbox().x - TextureFactory.getInstance().getTextureTongue().getHeight();
			y = world.getFrogger().getHitbox().y + (world.getFrogger().getHitbox().height / 2.f) - (TextureFactory.getInstance().getTextureTongue().getWidth() / 2.f);
		}
		else if(rota == world.getFrogger().getFaceRight()) {
			x = world.getFrogger().getHitbox().x + world.getFrogger().getHitbox().width;
			y = world.getFrogger().getHitbox().y + (world.getFrogger().getHitbox().height / 2.f) - (TextureFactory.getInstance().getTextureTongue().getWidth() / 2.f);
		}
				
		double time = world.getFactory().getAir_time();
		double speed = world.getFactory().getShot_speed();
		
		return (new Tongue(x, y, rota, time, speed));
	}
	
	private void shot_update(float delta) {
		// update the explosions
	    Iterator<Explosion> itr3 = world.getExplosions().iterator();
	    while (itr3.hasNext()) {
	    	Explosion e = itr3.next();
			
	    	e.update(delta); 	    					
				
	    	if(e.getTime() < 0)
	    		itr3.remove();
	    }
	    
    	Iterator<Tongue> itr = world.getShots().iterator();
	    while (itr.hasNext()) {
	    	Tongue e = itr.next();
			
	    	e.move(delta); 	    	
				
			if( (e.getPos().getX() > Gdx.graphics.getWidth()) || (e.getPos().getX() + e.getTexture().getWidth() < 0) ) {
				itr.remove();	
			}
			else if( (e.getPos().getY() > Gdx.graphics.getHeight()) || (e.getPos().getY() + e.getTexture().getHeight() < 0) ) {
				itr.remove();	
			}
			else if(e.isFacingUp() && (e.getPos().getY() - e.getInitialPos().getY() > e.getAir_time()) ) { // up
				itr.remove();
			}
			else if(e.isFacingDown() && (e.getInitialPos().getY() - e.getPos().getY() > e.getAir_time()) ) { // down
				itr.remove();
			}
			else if(e.isFacingLeft() && (e.getInitialPos().getX() - e.getPos().getX() > e.getAir_time()) ) { // left
				itr.remove();
			}
			else if(e.isFacingRight() && (e.getPos().getX() - e.getInitialPos().getX() > e.getAir_time()) ) { // right
				itr.remove();
			}
			else { // check if a fly is hit
				Iterator<FlyLinear> itr2 = world.getFliesLinear().iterator();
			    while (itr2.hasNext()) {
			    	FlyLinear fly = itr2.next();
			    	// we shoot a fly
			    	if(Intersector.overlaps(e.getHitbox(), fly.getHitbox())) {
			    		life++;
			    		world.getExplosions().add(new Explosion(fly.getPos().getX(), fly.getPos().getY(), 0));
			    		itr2.remove();	
			    		itr.remove();
					}
			    }
			}
	    }    
	}
	
	private void fliesLinear_update(float delta) {
		Iterator<FlyLinear> itr = world.getFliesLinear().iterator();
	    while (itr.hasNext()) {
	    	FlyLinear e = itr.next();
			
	    	e.move(delta); 
	    	
	    	if( (e.getPos().getX() > Gdx.graphics.getWidth()) || (e.getPos().getX() + e.getTexture().getWidth() < 0) ) {
				itr.remove();	
			}
	    	else if( (e.getPos().getY() > Gdx.graphics.getHeight()) || (e.getPos().getY() + e.getTexture().getHeight() < 0) ) {
				itr.remove();	
			}
	    }	
	        
	    timer_flies += delta;
	    double min = world.getFactory().getFlies_min();
		double max = world.getFactory().getFlies_max();
		int random_interval = (int)(((Math.random() * (max - min))) + min);
	    
	    if(timer_flies > random_interval) {
	    	create_flyLinear();    	
	    	timer_flies = 0;
	    }
	}
	
	public void create_flyLinear() {
		int first_border = (int) (Math.random() * 4);
		int second_border = 0;
		do {
			second_border = (int) (Math.random() * 4);
		} while(first_border == second_border);		

		double x_a=0, y_a=0, x_b=0, y_b=0;	
		double fly_width = TextureFactory.getInstance().getTextureFly().getWidth();
		double fly_height = TextureFactory.getInstance().getTextureFly().getWidth();
		
		switch(first_border) {
			case 0 : // Top border
				x_a = (Math.random() * Gdx.graphics.getWidth());
				y_a = Gdx.graphics.getHeight();
				
				switch(second_border) {
					case 1 : // Top to Bottom border
						x_b = (Math.random() * Gdx.graphics.getWidth());
						y_b = -fly_height;
						break;		
					case 2 : // Top to Left border
						x_b = -fly_width;
						y_b = (Math.random() * Gdx.graphics.getHeight());
						break;
					case 3 : // Top to Right border
						x_b = Gdx.graphics.getWidth();
						y_b = (Math.random() * Gdx.graphics.getHeight());
						break;
				}
			break;
				
			case 1 : // Bottom border
				x_a = (Math.random() * Gdx.graphics.getWidth());
				y_a = -fly_height;
				
				switch(second_border) {
					case 0 : // Bottom to Top border
						x_b = (Math.random() * Gdx.graphics.getWidth());
						y_b = Gdx.graphics.getHeight();
					break;
					case 2 : // Bottom to Left border
						x_b = -fly_width;
						y_b = (Math.random() * Gdx.graphics.getHeight());
						break;
					case 3 : // Bottom to Right border
						x_b = Gdx.graphics.getWidth();
						y_b = (Math.random() * Gdx.graphics.getHeight());
						break;
				}
			break;
				
			case 2 : // Left border
				x_a = -fly_width;
				y_a = (Math.random() * Gdx.graphics.getHeight());
				
				switch(second_border) {		
					case 0 : // Left to Top border
						x_b = (Math.random() * Gdx.graphics.getWidth());
						y_b = Gdx.graphics.getHeight();
						break;	
					case 1 : // Left to Bottom border
						x_b = (Math.random() * Gdx.graphics.getWidth());
						y_b = -fly_height;
						break;
					case 3 : // Left to Right border
						x_b = Gdx.graphics.getWidth();
						y_b = (Math.random() * Gdx.graphics.getHeight());
						break;
				}
			break;
			
			case 3 : // Right border
				x_a = Gdx.graphics.getWidth();
				y_a = (Math.random() * Gdx.graphics.getHeight());
				
				switch(second_border) {
					case 0 : // Right to Top border
						x_b = (Math.random() * Gdx.graphics.getWidth());
						y_b = Gdx.graphics.getHeight();
						break;	
					case 1 : // Right to Bottom border
						x_b = (Math.random() * Gdx.graphics.getWidth());
						y_b = -fly_height;
						break;		
					case 2 : // Right to Left border
						x_b = -fly_width;
						y_b = (Math.random() * Gdx.graphics.getHeight());
						break;
				}
			break;	
		}
		world.getFliesLinear().add(new FlyLinear(x_a, y_a, 0, x_b, y_b, world.getFactory().getFlies_speed()));
	}
	
	
	public FroggerWorld getWorld() {
		return world;
	}
	public double getScore() {
		return score;
	}
	public double getLife() {
		return life;
	}
	public boolean isVictory() {
		return victory;
	}	
}
