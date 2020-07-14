package realtimeEngine;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public final class RGControl {
	LinkedList<KeyBinding> keyBindingList = new LinkedList<KeyBinding>();
	LinkedList<MouseBinding> mouseBindingList = new LinkedList<MouseBinding>();

	private String name;

	public static int mouseWheelScrollAmount = 0;

	public RGControl(String name){
		this.name = name;
		RGInput.controlList.add(this);
	}

	public static RGControl newKeyControl(String name, int keyCode){
		for(RGControl control: RGInput.controlList){
			if(name.equals(control.name)){
				control.addKeyBinding(keyCode);
				return control;
			}
		}
		RGControl control = new RGControl(name);
		control.addKeyBinding(keyCode);
		return control;
	}

	public static RGControl newMouseControl(String name, int button){
		for(RGControl control: RGInput.controlList){
			if(name.equals(control.name)){
				control.addMouseBinding(button);
				return control;
			}
		}
		RGControl control = new RGControl(name);
		control.addMouseBinding(button);
		return control;
	}

	public static Point mousePos(){
		return RGInput.mouse;
	}

	public static KeyEvent getLastKeyEvent(boolean consume){
		KeyEvent lastKeyEvent = RGInput.lastKeyEvent;
		if(consume){
			RGInput.lastKeyEvent = null;
		}
		return lastKeyEvent;
	}

	public static MouseEvent getLastMouseEvent(boolean consume){
		MouseEvent lastMouseEvent = RGInput.lastMouseEvent;
		if(consume){
			RGInput.lastMouseEvent = null;
		}
		return lastMouseEvent;
	}
	
	public static boolean isAnythingPressed(){
		return isAnythingPressed(true, true);
	}
	
	public static boolean isAnythingPressed(boolean checkKeyPresses, boolean checkMousePresses){
		return (checkKeyPresses && !RGInput.keyPressList.isEmpty()) || (checkMousePresses && !RGInput.mousePressList.isEmpty());
	}

	public boolean hasKeyBinding(int keyCode){
		for(KeyBinding bind: keyBindingList){
			if(bind.keyCode == keyCode){
				return true;
			}
		}
		return false;
	}

	public boolean hasMouseBinding(int button){
		for(MouseBinding bind: mouseBindingList){
			if(bind.button == button){
				return true;
			}
		}
		return false;
	}

	public void addKeyBinding(int keyCode) {
		if(!hasKeyBinding(keyCode)){
			keyBindingList.add(new KeyBinding(keyCode));
		}
	}

	public void addMouseBinding(int button) {
		if(!hasMouseBinding(button)){
			mouseBindingList.add(new MouseBinding(button));
		}
	}

	public LinkedList<KeyBinding> getKeyBindings(){
		return keyBindingList;
	}

	public LinkedList<MouseBinding> getMouseBindings(){
		return mouseBindingList;
	}

	public void removeKeyBinding(int keyCode) {
		KeyBinding bindToRemove = null;
		for(KeyBinding bind: keyBindingList){
			if(bind.keyCode == keyCode){
				bindToRemove = bind;
			}
		}
		if(bindToRemove != null){
			keyBindingList.remove(bindToRemove);
		}
	}

	public void removeMouseBinding(int button) {
		MouseBinding bindToRemove = null;
		for(MouseBinding bind: mouseBindingList){
			if(bind.button == button){
				bindToRemove = bind;
			}
		}
		if(bindToRemove != null){
			mouseBindingList.remove(bindToRemove);
		}
	}

	public void clearKeyBindings(){
		keyBindingList.clear();
	}

	public void clearMouseBindings(){
		mouseBindingList.clear();
	}

	public boolean isPressed(){
		for(KeyBinding bind: keyBindingList){
			if(bind.isPressed()){
				return true;
			}
		}
		for(MouseBinding bind: mouseBindingList){
			if(bind.isPressed()){
				return true;
			}
		}
		return false;
	}

	public void setPressed(boolean pressed){
		for(KeyBinding bind: keyBindingList){
			bind.setPressed(pressed);
		}
		for(MouseBinding bind: mouseBindingList){
			bind.setPressed(pressed);
		}
	}
	
	public static void releaseAllPresses(){
		releaseAllKeyPresses();
		releaseAllMousePresses();
	}
	
	public static void releaseAllKeyPresses(){
		for(Integer keyPress: RGInput.keyPressList){
			RGInput.releaseKey(keyPress);
		}
	}
	
	public static void releaseAllMousePresses(){
		for(Integer mousePress: RGInput.mousePressList){
			RGInput.releaseMouse(mousePress);
		}
	}

	void pressKey(int keyCode) {
		for(KeyBinding bind: keyBindingList){
			bind.pressKey(keyCode);
		}
	}

	void releaseKey(int keyCode) {
		for(KeyBinding bind: keyBindingList){
			bind.releaseKey(keyCode);
		}
	}

	void pressMouse(int button) {
		for(MouseBinding bind: mouseBindingList){
			bind.pressMouse(button);
		}
	}

	void releaseMouse(int button) {
		for(MouseBinding bind: mouseBindingList){
			bind.releaseMouse(button);
		}
	}
	
	public String getName(){
		return name;
	}
}

class Binding {
	protected boolean pressed;

	void setPressed(boolean pressed){
		this.pressed = pressed;
	}

	boolean isPressed(){
		return pressed;
	}
}