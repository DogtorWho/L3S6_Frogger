package model;

import java.util.ArrayList;

import objects.factory.ObjectsFactory;
import objects.elements.Explosion;
import objects.elements.Fly;
import objects.elements.FlyLinear;
import objects.elements.Frogger;
import objects.elements.GameElement;
import objects.elements.linears.Tongue;
import objects.elements.linears.vehicules.Vehicule;
import objects.elements.linears.water.WaterElement;

public class FroggerWorld {
	ObjectsFactory factory;
	private ArrayList<GameElement> elements;
	private ArrayList<ArrayList<WaterElement>> elementsWater;
	private ArrayList<ArrayList<Vehicule>> elementsRoad;
	private ArrayList<Tongue> shots;
	private ArrayList<FlyLinear> fliesLinear;
	private ArrayList<Explosion> explosions;
	
	public FroggerWorld() {
		factory = new ObjectsFactory();
		elements = new ArrayList<GameElement>();
		elementsWater = new ArrayList<ArrayList<WaterElement>>();
		elementsRoad = new ArrayList<ArrayList<Vehicule>>();
		shots = new ArrayList<Tongue>();
		fliesLinear = new ArrayList<FlyLinear>();
		//fliesLinear.add(new FlyLinear(0, Math.random() * Gdx.graphics.getHeight(), 0, factory.getFlies_speed()));
		explosions = new ArrayList<Explosion>();
	}
	
	public void init(String filename) {
		elements = factory.get_GameElements(filename);	
		elementsWater = factory.get_GameElementsWater(filename);	
		elementsRoad = factory.get_GameElementsRoad(filename);
	}
	
	public Fly getFly() {
		for(GameElement e : elements)
			if(e instanceof Fly)
				return (Fly) e;
		return null;
	}
	
	public Frogger getFrogger() {
		for(GameElement e : elements)
			if(e instanceof Frogger)
				return (Frogger) e;
		return null;
	}

	public ArrayList<GameElement> getElements() {
		return elements;
	}
	public void setElements(ArrayList<GameElement> elements) {
		this.elements = elements;
	}

	public ArrayList<ArrayList<WaterElement>> getElementsWater() {
		return elementsWater;
	}
	public void setElementsWater(ArrayList<ArrayList<WaterElement>> elementsWater) {
		this.elementsWater = elementsWater;
	}
	
	public ArrayList<ArrayList<Vehicule>> getElementsRoad() {
		return elementsRoad;
	}
	public void setElementsRoad(ArrayList<ArrayList<Vehicule>> elementsRoad) {
		this.elementsRoad = elementsRoad;
	}
	public ArrayList<Tongue> getShots() {
		return shots;
	}
	public ArrayList<FlyLinear> getFliesLinear() {
		return fliesLinear;
	}
	public ArrayList<Explosion> getExplosions() {
		return explosions;
	}	
	
	public ObjectsFactory getFactory() {
		return factory;
	}
}
