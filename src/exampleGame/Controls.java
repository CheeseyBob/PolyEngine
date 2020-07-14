package exampleGame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import polyEngine.ControlList;

import realtimeEngine.RGControl;

class Controls implements ControlList {
	
	static RGControl moveUp = new RGControl("Move Up");
	static RGControl moveDown = new RGControl("Move Down");
	static RGControl moveLeft = new RGControl("Move Left");
	static RGControl moveRight = new RGControl("Move Right");
	static RGControl rotateCW = new RGControl("Rotate CW");
	static RGControl rotateACW = new RGControl("Rotate ACW");
	static RGControl triggerLeft = new RGControl("Left Trigger");
	
	public void setupControls(){
		moveUp.addKeyBinding(KeyEvent.VK_UP);
		moveDown.addKeyBinding(KeyEvent.VK_DOWN);
		moveLeft.addKeyBinding(KeyEvent.VK_LEFT);
		moveRight.addKeyBinding(KeyEvent.VK_RIGHT);
		moveUp.addKeyBinding(KeyEvent.VK_W);
		moveDown.addKeyBinding(KeyEvent.VK_S);
		moveLeft.addKeyBinding(KeyEvent.VK_A);
		moveRight.addKeyBinding(KeyEvent.VK_D);
		
		rotateCW.addKeyBinding(KeyEvent.VK_E);
		rotateACW.addKeyBinding(KeyEvent.VK_Q);
		
		triggerLeft.addMouseBinding(MouseEvent.BUTTON1);
	}
}