package realtimeEngine;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.LinkedList;

public class RGInput implements KeyListener, MouseListener, MouseWheelListener {
	public static Point mouse = new Point(0, 0);
	static KeyEvent lastKeyEvent = null;
	static MouseEvent lastMouseEvent = null;

	static LinkedList<Integer> keyPressList = new LinkedList<Integer>();
	static LinkedList<Integer> mousePressList = new LinkedList<Integer>();
	
	static LinkedList<RGControl> controlList = new LinkedList<RGControl>();

	public static void updateMouse(Point p){
		if(p != null){
			mouse.setLocation(p);
		}
	}

	public void keyPressed(KeyEvent e) {		//This method gets called by the VM
		lastKeyEvent = e;
		pressKey(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {		//This method gets called by the VM
		releaseKey(e.getKeyCode());
	}

	public void mousePressed(MouseEvent e) {	//This method gets called by the VM
		lastMouseEvent = e;
		pressMouse(e.getButton());
	}

	public void mouseReleased(MouseEvent e) {	//This method gets called by the VM
		releaseMouse(e.getButton());
	}

	static void pressKey(int keyCode) {				//This method gets called by RGInput and RGControl
		keyPressList.add(keyCode);
		for(RGControl control: controlList){
			control.pressKey(keyCode);
		}
	}

	static void releaseKey(int keyCode) {				//This method gets called by RGInput and RGControl
		Iterator<Integer> it = keyPressList.iterator();
		while(it.hasNext()){
			if(it.next().equals(keyCode)){
				it.remove();
			}
		}
		for(RGControl control: controlList){
			control.releaseKey(keyCode);
		}
	}

	static void pressMouse(int button) {				//This method gets called by RGInput and RGControl
		mousePressList.add(button);
		for(RGControl control: controlList){
			control.pressMouse(button);
		}
	}

	static void releaseMouse(int button) {				//This method gets called by RGInput and RGControl
		Iterator<Integer> it = mousePressList.iterator();
		while(it.hasNext()){
			if(it.next().equals(button)){
				it.remove();
			}
		}
		for(RGControl control: controlList){
			control.releaseMouse(button);
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		RGControl.mouseWheelScrollAmount += e.getWheelRotation();
	}

	public void mouseEntered(MouseEvent e) {
		return;
	}

	public void mouseExited(MouseEvent e) {
		return;
	}

	public void keyTyped(KeyEvent key) {
		return;
	}

	public void mouseClicked(MouseEvent e) {
		return;
	}
}