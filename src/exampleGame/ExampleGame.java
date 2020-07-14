package exampleGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import polyEngine.*;
import realtimeEngine.RealtimeGame;

class ExampleGame implements RealtimeGame {
	static Font bigFont = new Font("arial", Font.PLAIN, 32);
	static Scene scene0 = new Scene0();
	static Scene scene1 = new Scene1();
	static Scene scene2 = new Scene2();
	
	public static void main(String[] args){
		PolyEngine.startup(new ExampleGame(), "Poly Engine Test", 800, 600, false, new Controls());
		PolyEngine.antialiasingType = PolyEngine.ANTIALIASING_BICUBIC;
		PolyEngine.drawWireframe = true;
	}
	
	public void loadGame() {
		PolyEngine.backgroundColor = Color.DARK_GRAY;
		PolyEngine.loadScene(scene0, true);
	}
	
	public void step(double tpf) {
		PolyEngine.step(tpf);
	}
	
	public void draw(Graphics2D g) {
		PolyEngine.draw(g);
	}
}