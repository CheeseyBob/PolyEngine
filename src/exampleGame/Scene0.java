package exampleGame;
import java.awt.Graphics2D;
import java.util.LinkedList;

import polyEngine.*;

class Scene0 extends Scene {
	LinkedList<Object> objectList = new LinkedList<Object>();
	
	Scene0(){
		// UI positions are relative to the top left of the window. //
		objectList.add(new UIButton_StartGame(200, 200, Sprites.button));
		
		// Game object potitions are relative to the game world. //
		objectList.add(new SpriteEffect(200.0, 200.0, 0.0, false, Sprites.campfire));
	}
	
	public LinkedList<Object> getObjectList() {
		return objectList;
	}
	
	public void draw(Graphics2D g) {
	}
	
	public void load(){
	}
	
	public void step(double tpf) {
	}
}