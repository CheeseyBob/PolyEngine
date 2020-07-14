package exampleGame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import polyEngine.*;

class Scene2 extends Scene {
	LinkedList<Object> objectList = new LinkedList<Object>();
	
	Scene2(){
		
	}
	
	public LinkedList<Object> getObjectList() {
		return objectList;
	}
	
	public void step(double tpf) {
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.setFont(ExampleGame.bigFont);
		g.drawString("end", 200, 200);
	}
	
	public void load(){
	}
}