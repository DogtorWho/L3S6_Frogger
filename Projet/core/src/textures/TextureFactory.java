package textures;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class TextureFactory {
	private static TextureFactory instance = new TextureFactory();
	
	private String image_folder = "images/";
	
	private Texture default_texture;
	
	private Texture life;
	private Texture explosion;
	
	private Texture background;
	
	private Texture frogger;
	private ArrayList<Texture> frogger_anim;
	
	private Texture fly;
	private Texture tongue;
	
	private Texture green_car;
	private Texture blue_car;
	private Texture yellow_car;
	private Texture brown_truck;
	private Texture red_truck;
	private ArrayList<Texture> vehicules;
	
	private Texture large_log;
	private Texture medium_log;
	private Texture small_log;
	private Texture turtle;
	private ArrayList<Texture> logs;
	
	
	@SuppressWarnings("serial")
	private TextureFactory() {
		default_texture = new Texture(image_folder + "default.png");
		
		life = new Texture(image_folder + "heart.png");
		explosion = new Texture(image_folder + "explosion.png");
		
		background = new Texture(image_folder + "background.png");
		
		frogger = new Texture(image_folder + "frogger_1.png");				
		frogger_anim = new ArrayList<Texture>() {
			{
				add(new Texture(image_folder + "frogger_1.png"));
				add(new Texture(image_folder + "frogger_2.png"));
				add(new Texture(image_folder + "frogger_3.png"));
				add(new Texture(image_folder + "frogger_4.png"));
				add(new Texture(image_folder + "frogger_5.png"));
				add(new Texture(image_folder + "frogger_6.png"));
				add(new Texture(image_folder + "frogger_7.png"));
			}
		};
		
		fly = new Texture(image_folder + "fly.png");
		tongue = new Texture(image_folder + "spit_2.png");
		
		green_car = new Texture(image_folder + "greenCar.png");
		blue_car = new Texture(image_folder + "blueCar.png");
		yellow_car = new Texture(image_folder + "yellowCar.png");
		brown_truck = new Texture(image_folder + "brownTruck.png");
		red_truck = new Texture(image_folder + "redTruck.png");
		vehicules = new ArrayList<Texture>() {
			{
				add(green_car);
				add(blue_car);
				add(yellow_car);
				add(brown_truck);
				add(red_truck);
			}
		};
		
		large_log = new Texture(image_folder + "log_large.png");
		medium_log = new Texture(image_folder + "log_medium.png");	
		small_log = new Texture(image_folder + "log_small.png");		
		logs = new ArrayList<Texture>() {
			{
				add(large_log);
				add(medium_log);
				add(small_log);
			}
		};
			
		turtle = new Texture(image_folder + "turtle.png");
	}
	
	
	
	public static TextureFactory getInstance() {
		return instance;
	}
	
	
	public Texture getTextureDefault() {
		return default_texture;
	}
	
	public Texture getTextureLife() {
		return life;
	}
	
	public Texture getTextureExplosion() {
		return explosion;
	}
	
	public Texture getTextureBackground() {
		return background;
	}
	
	public Texture getTextureFrogger() {
		return frogger;
	}
	public ArrayList<Texture> getFroggerAnim() {
		return frogger_anim;
	}
	public Texture getFrameFrogger(int i) {
		if(i < frogger_anim.size())
			return frogger_anim.get(i);
		else
			System.out.println("The animation don't have this many frames");
		return frogger_anim.get(0);
	}
	
	public Texture getTextureFly() {
		return fly;
	}
	
	public Texture getTextureTongue() {
		return tongue;
	}

	
	/* Vehicules textures */
	public Texture getTextureVehicule() {
		int i = (int)(Math.random() * vehicules.size());	
		return vehicules.get(i);
	}
	
	public Texture getTextureGreenCar() {
		return green_car;
	}
	
	public Texture getTextureBlueCar() {
		return blue_car;
	}
	
	public Texture getTextureYellowCar() {
		return yellow_car;
	}
	
	public Texture getTextureBrownTruck() {
		return brown_truck;
	}
	
	public Texture getTextureRedTruck() {
		return red_truck;
	}
	
	
	/* Logs and turtle textures */
	public Texture getTextureLog() {
		int i = (int)(Math.random() * logs.size());	
		return logs.get(i);
	}
	
	public Texture getTextureLargeLog() {
		return large_log;
	}
	
	public Texture getTextureMediumLog() {
		return medium_log;
	}
	
	public Texture getTextureSmallLog() {
		return small_log;
	}

	public Texture getTextureTurtle() {
		return turtle;
	}
}
