package objects.factory;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.badlogic.gdx.Gdx;

import utils.Vector2f;
import objects.elements.Fly;
import objects.elements.Frogger;
import objects.elements.GameElement;
import objects.elements.linears.vehicules.Road;
import objects.elements.linears.vehicules.Vehicule;
import objects.elements.linears.vehicules.cars.BlueCar;
import objects.elements.linears.vehicules.cars.GreenCar;
import objects.elements.linears.vehicules.cars.YellowCar;
import objects.elements.linears.vehicules.trucks.BrownTruck;
import objects.elements.linears.vehicules.trucks.RedTruck;
import objects.elements.linears.water.Turtle;
import objects.elements.linears.water.WaterElement;
import objects.elements.linears.water.WaterStream;
import objects.elements.linears.water.logs.LargeLog;
import objects.elements.linears.water.logs.MediumLog;
import objects.elements.linears.water.logs.SmallLog;

public class ObjectsFactory {
	private int life_number;
	private double timer_fly;
	private double frogger_cooldown;
	private double speed_percent;
	private double air_time;
	private double shot_number;
	private double shot_cooldown;
	private double shot_speed;
	private double flies_min;
	private double flies_max;
	private double flies_speed;
	
	private ArrayList<Vector2f> fly_positions;
	
	private ArrayList<WaterStream> water_lines;
	private ArrayList<Road> road_lines;
	
	public ObjectsFactory() {
		life_number = 0;
		timer_fly = 0;
		frogger_cooldown = 0;
		speed_percent = 0;
		air_time = 0;
		shot_number = 0;
		shot_cooldown = 0;
		shot_speed = 0;	
		flies_min = 0;
		flies_max = 0;
		flies_speed = 0;
		
		fly_positions = new ArrayList<Vector2f>();
		
		water_lines = new ArrayList<WaterStream>();
		road_lines = new ArrayList<Road>();
	}
	
	private Frogger make_frogger(Node node) {
		Element e = (Element)node;
		
		Frogger frogger = new Frogger( Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()),
					Double.parseDouble(e.getElementsByTagName("PosY").item(0).getTextContent()),
					Double.parseDouble(e.getElementsByTagName("Rotation").item(0).getTextContent()) );

		frogger_cooldown = Double.parseDouble(e.getElementsByTagName("Cooldown").item(0).getTextContent());
		
		return frogger;
	}
	
	
	private Fly make_fly(Node node) {
		Fly fly = new Fly(0, 0, 0);
		fly.setPossible_positions(fly_positions);		
		fly.changePosition();
		
		return fly;
	}
	
	
	private LargeLog make_largeLog(Node node) {
		Element e = (Element)node;	
		LargeLog log = new LargeLog(0, 0, water_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));
		if(e.getElementsByTagName("PosX").item(0) != null)
			log.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return log;
	}
	
	private MediumLog make_mediumLog(Node node) {
		Element e = (Element)node;	
		MediumLog log = new MediumLog(0, 0, water_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));
		if(e.getElementsByTagName("PosX").item(0) != null)
			log.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return log;
	}
	
	private SmallLog make_smallLog(Node node) {
		Element e = (Element)node;
		SmallLog log = new SmallLog(0, 0, water_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));
		if(e.getElementsByTagName("PosX").item(0) != null)
			log.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return log;
	}
	
	private Turtle make_turtle(Node node) {
		Element e = (Element)node;	
		Turtle turtle = new Turtle(0, 0, water_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));	
		if(e.getElementsByTagName("PosX").item(0) != null)
			turtle.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return turtle;
	}
	
	private GreenCar make_greenCar(Node node) {
		Element e = (Element)node;	
		GreenCar vehicule = new GreenCar(0, 0, road_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));
		if(e.getElementsByTagName("PosX").item(0) != null)
			vehicule.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return vehicule;
	}
	
	private BlueCar make_blueCar(Node node) {
		Element e = (Element)node;
		BlueCar vehicule = new BlueCar(0, 0, road_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));	
		if(e.getElementsByTagName("PosX").item(0) != null)
			vehicule.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return vehicule;
	}
	
	private YellowCar make_yellowCar(Node node) {
		Element e = (Element)node;	
		YellowCar vehicule = new YellowCar(0, 0, road_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));	
		if(e.getElementsByTagName("PosX").item(0) != null)
			vehicule.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return vehicule;
	}
	
	private BrownTruck make_brownTruck(Node node) {
		Element e = (Element)node;		
		BrownTruck vehicule = new BrownTruck(0, 0, road_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));
		if(e.getElementsByTagName("PosX").item(0) != null)
			vehicule.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return vehicule;
	}
	
	private RedTruck make_redTruck(Node node) {
		Element e = (Element)node;		
		RedTruck vehicule = new RedTruck(0, 0, road_lines.get(Integer.parseInt(e.getElementsByTagName("Position").item(0).getTextContent())));
		if(e.getElementsByTagName("PosX").item(0) != null)
			vehicule.getPos().setX(Double.parseDouble(e.getElementsByTagName("PosX").item(0).getTextContent()));
		return vehicule;
	}
	
	private ArrayList<Vector2f> get_positions(Node node) {
		ArrayList<Vector2f> positions = new ArrayList<Vector2f>();
		
		Element e = (Element)node;
		NodeList slots = e.getElementsByTagName("Slot");
		
		for(int j=0; j < slots.getLength(); j++) {
			Node n = slots.item(j);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element pos = (Element)n;
				
				int x=0, y=0;
				x = Integer.parseInt(pos.getElementsByTagName("PosX").item(0).getTextContent());
				y = Integer.parseInt(pos.getElementsByTagName("PosY").item(0).getTextContent());
				
				positions.add(new Vector2f(x, y));
			}	
		}
		
		if(slots.getLength() == 0 || positions.isEmpty())
			Gdx.app.log("XML", "issue with getting the positions list, check if the -Slot- tag is used in the XML file");
		
		return positions;
	}
	
	private void fill_roadPositions(Node node) {		
		Element e = (Element)node;
		NodeList slots = e.getElementsByTagName("Slot");
		
		for(int j=0; j < slots.getLength(); j++) {
			Node n = slots.item(j);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element pos = (Element)n;
				
				int num=0;
				double x=0, y=0, rota=0, speed=0, min=0, max=0;
				x = Double.parseDouble(pos.getElementsByTagName("PosX").item(0).getTextContent());
				y = Double.parseDouble(pos.getElementsByTagName("PosY").item(0).getTextContent());
				num = Integer.parseInt(pos.getElementsByTagName("Number").item(0).getTextContent());
				rota = Double.parseDouble(pos.getElementsByTagName("Rotation").item(0).getTextContent());
				speed = Double.parseDouble(pos.getElementsByTagName("Speed").item(0).getTextContent());
				min = Double.parseDouble(pos.getElementsByTagName("IntervalMin").item(0).getTextContent());
				max = Double.parseDouble(pos.getElementsByTagName("IntervalMax").item(0).getTextContent());
				
				road_lines.add(new Road(x, y, num, rota, speed, min, max));
			}	
		}
		
		if(slots.getLength() == 0 || road_lines.isEmpty())
			Gdx.app.log("XML", "issue with getting the roads positions list, check if the -Slot- tag is used in the XML file");
	}
	
	private void fill_waterPositions(Node node) {		
		Element e = (Element)node;
		NodeList slots = e.getElementsByTagName("Slot");
		
		for(int j=0; j < slots.getLength(); j++) {
			Node n = slots.item(j);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				Element pos = (Element)n;
				
				int num=0;
				double x=0, y=0, rota=0, speed=0, min=0, max=0;
				x = Double.parseDouble(pos.getElementsByTagName("PosX").item(0).getTextContent());
				y = Double.parseDouble(pos.getElementsByTagName("PosY").item(0).getTextContent());
				num = Integer.parseInt(pos.getElementsByTagName("Number").item(0).getTextContent());
				rota = Double.parseDouble(pos.getElementsByTagName("Rotation").item(0).getTextContent());
				speed = Double.parseDouble(pos.getElementsByTagName("Speed").item(0).getTextContent());
				min = Double.parseDouble(pos.getElementsByTagName("IntervalMin").item(0).getTextContent());
				max = Double.parseDouble(pos.getElementsByTagName("IntervalMax").item(0).getTextContent());
				
				water_lines.add(new WaterStream(x, y, num, rota, speed, min, max));
			}	
		}
		
		if(slots.getLength() == 0 || water_lines.isEmpty())
			Gdx.app.log("XML", "issue with getting the water positions list, check if the -Slot- tag is used in the XML file");
	}
	
	
	public ArrayList<GameElement> get_GameElements(String filename) {
		ArrayList<GameElement> elements = new ArrayList<GameElement>();
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filename);
			doc.getDocumentElement().normalize();
			
			if(doc.getDocumentElement().getNodeName() == "World") {
				NodeList list = doc.getElementsByTagName("Element");
				for(int i=0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element)node;
						
						if(e.getAttribute("id").equals("FlyPositions")) {
							fly_positions = get_positions(node);
						}
						
						/*switch(e.getAttribute("id")) {						
							case "FlyPositions" : fly_positions = get_positions(node);
								break;
						}*/
					}			
				}
							
				for(int i=0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element)node;
						
						String id = e.getAttribute("id");
						
						// ifs instead of a switch because of Gradle
						if(id.equals("Life")) {
							life_number = Integer.parseInt(e.getElementsByTagName("Number").item(0).getTextContent());
						}
						else if(id.equals("Timer")) {
							timer_fly = Double.parseDouble(e.getElementsByTagName("FlyTimer").item(0).getTextContent());
						}
						else if(id.equals("SpeedPercent")) {
							speed_percent = Double.parseDouble(e.getElementsByTagName("Percent").item(0).getTextContent());
						}
						else if(id.equals("Tongue")) {
							air_time = Double.parseDouble(e.getElementsByTagName("AirTime").item(0).getTextContent());
							shot_number = Double.parseDouble(e.getElementsByTagName("Shot").item(0).getTextContent());
							shot_cooldown = Double.parseDouble(e.getElementsByTagName("Cooldown").item(0).getTextContent());
							shot_speed = Double.parseDouble(e.getElementsByTagName("Speed").item(0).getTextContent());
						}
						else if(id.equals("FliesLinear")) {
							flies_min = Double.parseDouble(e.getElementsByTagName("IntervalMin").item(0).getTextContent());
							flies_max = Double.parseDouble(e.getElementsByTagName("IntervalMax").item(0).getTextContent());
							flies_speed = Double.parseDouble(e.getElementsByTagName("Speed").item(0).getTextContent());
						}
						else if(id.equals("Frogger")) {
							elements.add(make_frogger(node));
						}
						else if(id.equals("Fly")) {
							elements.add(make_fly(node));
						}

						/*switch(e.getAttribute("id")) {
							case "Life" : life_number = Integer.parseInt(e.getElementsByTagName("Number").item(0).getTextContent());
								break;
							case "Timer" : timer_fly = Double.parseDouble(e.getElementsByTagName("FlyTimer").item(0).getTextContent());
								break;
							case "SpeedPercent" : speed_percent = Double.parseDouble(e.getElementsByTagName("Percent").item(0).getTextContent());
								break;
							case "Tongue" : air_time = Double.parseDouble(e.getElementsByTagName("AirTime").item(0).getTextContent());
											shot_number = Double.parseDouble(e.getElementsByTagName("Shot").item(0).getTextContent());
											shot_cooldown = Double.parseDouble(e.getElementsByTagName("Cooldown").item(0).getTextContent());
											shot_speed = Double.parseDouble(e.getElementsByTagName("Speed").item(0).getTextContent());
								break;
							case "FliesLinear" : flies_min = Double.parseDouble(e.getElementsByTagName("IntervalMin").item(0).getTextContent());
												 flies_max = Double.parseDouble(e.getElementsByTagName("IntervalMax").item(0).getTextContent());
												 flies_speed = Double.parseDouble(e.getElementsByTagName("Speed").item(0).getTextContent());
								break;
								
							case "Frogger" : elements.add(make_frogger(node));
								break;
							case "Fly" : elements.add(make_fly(node));
								break;
						}*/
					}	
				}
			}
			else {
				Gdx.app.log("XML", "wrong root name, can't transform the file into objects");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return elements;
	}
	
	
	public ArrayList<ArrayList<WaterElement>> get_GameElementsWater(String filename) {
		ArrayList<ArrayList<WaterElement>> elementsWater = new ArrayList<ArrayList<WaterElement>>();
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filename);
			doc.getDocumentElement().normalize();
			
			if(doc.getDocumentElement().getNodeName() == "World") {
				NodeList list = doc.getElementsByTagName("Element");
				for(int i=0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element)node;
						
						if(e.getAttribute("id").equals("RiverPositions")) {
							fill_waterPositions(node);	
						}
						
						/*switch(e.getAttribute("id")) {						
							case "RiverPositions" : fill_waterPositions(node);
								break;
						}*/
					}			
				}
				
				for(int i=0; i < water_lines.size(); i++)
					elementsWater.add(new ArrayList<WaterElement>());
							
				for(int i=0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element)node;
						
						String id = e.getAttribute("id");
						
						// ifs instead of a switch because of Gradle
						if(id.equals("LargeLog")) {
							LargeLog large_log = make_largeLog(node);
							elementsWater.get(large_log.getWater_line().getLine_number()).add(large_log);
						}
						else if(id.equals("MediumLog")) {
							MediumLog medium_log = make_mediumLog(node);
							elementsWater.get(medium_log.getWater_line().getLine_number()).add(medium_log);
						}
						else if(id.equals("SmallLog")) {
							SmallLog small_log = make_smallLog(node);
							elementsWater.get(small_log.getWater_line().getLine_number()).add(small_log);
						}
						else if(id.equals("Turtle")) {
							Turtle turtle = make_turtle(node);
							elementsWater.get(turtle.getWater_line().getLine_number()).add(turtle);
						}
						
						/*switch(e.getAttribute("id")) {						
							case "LargeLog" : LargeLog large_log = make_largeLog(node);
									elementsWater.get(large_log.getWater_line().getLine_number()).add(large_log);
								break;
							case "MediumLog" : MediumLog medium_log = make_mediumLog(node);
									elementsWater.get(medium_log.getWater_line().getLine_number()).add(medium_log);
								break;
							case "SmallLog" : SmallLog small_log = make_smallLog(node);
									elementsWater.get(small_log.getWater_line().getLine_number()).add(small_log);
								break;
							case "Turtle" : Turtle turtle = make_turtle(node);
									elementsWater.get(turtle.getWater_line().getLine_number()).add(turtle);
								break;
						}*/
					}	
				}
			}
			else {
				Gdx.app.log("XML", "wrong root name, can't transform the file into objects");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return elementsWater;
	}
	
	
	public ArrayList<ArrayList<Vehicule>> get_GameElementsRoad(String filename) {
		ArrayList<ArrayList<Vehicule>> elementsRoad = new ArrayList<ArrayList<Vehicule>>();
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filename);
			doc.getDocumentElement().normalize();
			
			if(doc.getDocumentElement().getNodeName() == "World") {
				NodeList list = doc.getElementsByTagName("Element");
				for(int i=0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element)node;
						
						if(e.getAttribute("id").equals("RoadPositions")) {
							fill_roadPositions(node);
						}
						
						/*switch(e.getAttribute("id")) {						
							case "RoadPositions" : fill_roadPositions(node);
								break;
						}*/
					}			
				}
				
				for(int i=0; i < road_lines.size(); i++)
					elementsRoad.add(new ArrayList<Vehicule>());
							
				for(int i=0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element)node;
						
						String id = e.getAttribute("id");
						
						// ifs instead of a switch because of Gradle
						if(id.equals("BlueCar")) {
							BlueCar blue_car = make_blueCar(node);
							elementsRoad.get(blue_car.getRoad_line().getLine_number()).add(blue_car);
						}
						else if(id.equals("GreenCar")) {
							GreenCar green_car = make_greenCar(node);
							elementsRoad.get(green_car.getRoad_line().getLine_number()).add(green_car);
						}
						else if(id.equals("YellowCar")) {
							YellowCar yellow_car = make_yellowCar(node);
							elementsRoad.get(yellow_car.getRoad_line().getLine_number()).add(yellow_car);
						}
						else if(id.equals("BrownTruck")) {
							BrownTruck brown_truck = make_brownTruck(node);
							elementsRoad.get(brown_truck.getRoad_line().getLine_number()).add(brown_truck);
						}
						else if(id.equals("RedTruck")) {
							RedTruck red_truck = make_redTruck(node);
							elementsRoad.get(red_truck.getRoad_line().getLine_number()).add(red_truck);
						}
						
						/*switch(e.getAttribute("id")) {						
							case "BlueCar" : BlueCar blue_car = make_blueCar(node);
									elementsRoad.get(blue_car.getRoad_line().getLine_number()).add(blue_car);
								break;
							case "GreenCar" : GreenCar green_car = make_greenCar(node);
									elementsRoad.get(green_car.getRoad_line().getLine_number()).add(green_car);
								break;
							case "YellowCar" : YellowCar yellow_car = make_yellowCar(node);
									elementsRoad.get(yellow_car.getRoad_line().getLine_number()).add(yellow_car);
								break;
							case "BrownTruck" : BrownTruck brown_truck = make_brownTruck(node);
									elementsRoad.get(brown_truck.getRoad_line().getLine_number()).add(brown_truck);
								break;
							case "RedTruck" : RedTruck red_truck = make_redTruck(node);
									elementsRoad.get(red_truck.getRoad_line().getLine_number()).add(red_truck);
								break;
						}*/
					}	
				}
			}
			else {
				Gdx.app.log("XML", "wrong root name, can't transform the file into objects");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return elementsRoad;
	}
	
	
	public int getLife_number() {
		return life_number;
	}
	
	public double getTimer_fly() {
		return timer_fly;
	}
	
	public double getFrogger_cooldown() {
		return frogger_cooldown;
	}
	
	public double getSpeed_percent() {
		return speed_percent;
	}
	
	public double getAir_time() {
		return air_time;
	}
	public double getShot_number() {
		return shot_number;
	}
	public double getShot_cooldown() {
		return shot_cooldown;
	}
	public double getShot_speed() {
		return shot_speed;
	}
		
	
	public double getFlies_min() {
		return flies_min;
	}
	public double getFlies_max() {
		return flies_max;
	}
	public double getFlies_speed() {
		return flies_speed;
	}

	public ArrayList<Vector2f> getFly_positions() {
		return fly_positions;
	}

	public ArrayList<WaterStream> getWater_lines() {
		return water_lines;
	}
	public ArrayList<Road> getRoad_lines() {
		return road_lines;
	}	
}
