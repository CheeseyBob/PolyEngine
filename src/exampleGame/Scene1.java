package exampleGame;
import java.awt.Graphics2D;
import java.util.LinkedList;

import audio.SoundHandler;
import polyEngine.*;

class Scene1 extends Scene {
	LinkedList<Object> objectList = new LinkedList<Object>();
	StaticSpriteInstance prop1 = new StaticSpriteInstance(300, 200, 0.0, false, Sprites.apple);
	SpriteEffect prop2 = new SpriteEffect(300, 100, 0.0, false, Sprites.campfire);
	static Actor player = new Actor(200, 200, 0, Sprites.apple, Meshes.octagon(16), true);
	static double playerMoveSpeed = 200;
	static double playerRotationSpeed = Math.PI;
	static int score = 0;
	
	Scene1(){
		objectList.add(player);
		objectList.add(prop1);
		objectList.add(prop2);
		objectList.add(new Barrel(0, 100, (float)Math.PI/4, Sprites.barrel, Meshes.octagon(64), true));
		objectList.add(new Barrel(100, 100, (float)Math.PI/4, Sprites.barrel, Meshes.octagon(64), true));
	}
	
	public LinkedList<Object> getObjectList() {
		return objectList;
	}
	
	public void step(double tpf) {
		if(Controls.moveUp.isPressed()){
			player.move(0, -tpf*playerMoveSpeed);
		}
		if(Controls.moveDown.isPressed()){
			player.move(0, tpf*playerMoveSpeed);
		}
		if(Controls.moveLeft.isPressed()){
			player.move(-tpf*playerMoveSpeed, 0);
		}
		if(Controls.moveRight.isPressed()){
			player.move(tpf*playerMoveSpeed, 0);
		}
		if(Controls.rotateCW.isPressed()){
			player.rotate(tpf*playerRotationSpeed);
		}
		if(Controls.rotateACW.isPressed()){
			player.rotate(-tpf*playerRotationSpeed);
		}
		if(Controls.triggerLeft.isPressed()){
			Controls.triggerLeft.setPressed(false);
			SoundHandler.playSound(Sounds.shot1);
			Bullet bullet = new Bullet(player.getX(), player.getY(), 1000, player.getRotation());
			PolyEngine.place(bullet);
			bullet.setLocation(bullet.getX() + bullet.dx/60, bullet.getY() + bullet.dy/60);
		}
		PolyEngine.centreViewOn(player.getX(), player.getY());
		if(score >= 2){
			PolyEngine.loadScene(ExampleGame.scene2, true);
		}
	}
	
	public void draw(Graphics2D g) {
	}
	
	public void load(){
	}
}