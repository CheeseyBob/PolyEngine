package polyEngine;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import realtimeEngine.RGControl;

public abstract class UIButton implements Stepable, InterfaceElement {
	public static RGControl control = RGControl.newMouseControl("Select", MouseEvent.BUTTON1);

	private boolean isHovered = false;
	private boolean isClicked = false;
	private boolean controlPressed = false;
	
	public final boolean isClicked(){
		return isClicked;
	}
	
	public final boolean isHovered(){
		return isHovered;
	}
	
	public void step(double tpf) {
		boolean hovered = containsPoint(RGControl.mousePos());
		if(hovered){
			if(isHovered){
				onHover();
			} else {
				isHovered = true;
				onEnter();
			}
		} else if(isHovered){
			isHovered = false;
			onLeave();
		}
		if(control.isPressed()){
			if(isClicked){
				onDrag();
			} else if(hovered){
				if(!controlPressed){
					isClicked = true;
					onClick(controlPressed);
				}
			}
			controlPressed = true;
		} else {
			if(isClicked){
				isClicked = false;
				onRelease(hovered);
			}
			controlPressed = false;
		}
	}
	
	/**
	 * Called to determine whether the curser is over this button.
	 * @param p the location being tested.
	 * @return
	 */
	protected abstract boolean containsPoint(Point p);
	
	public abstract void draw(Graphics2D g);
	
	public abstract float getZDepth();
	
	/**
	 * Called once when the control is pressed, if the mouse is over this button.
	 * @param draggedOver
	 */
	protected abstract void onClick(boolean draggedOver);
	
	/**
	 * After this button is clicked, this method is called once each frame until the control is released.
	 */
	protected abstract void onDrag();
	
	/**
	 * Called whenever the control is released.
	 * @param releasedOverThis whether the curser is over this button.
	 */
	protected abstract void onRelease(boolean releasedOverThis);
	
	/**
	 * Called once when the curser goes from not being over this button being to over it.
	 */
	protected abstract void onEnter();
	
	/**
	 * After this button is hovered, this method is called once per frame until the curser is no longer over the button.
	 */
	protected abstract void onHover();
	
	/**
	 * Called once when the curser goes from being over this button to not being over it.
	 */
	protected abstract void onLeave();
}