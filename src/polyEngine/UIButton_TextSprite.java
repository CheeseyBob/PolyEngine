package polyEngine;

import general.Util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import realtimeEngine.RGControl;

public abstract class UIButton_TextSprite extends UIButton {
	public static RGControl control = RGControl.newMouseControl("Select", MouseEvent.BUTTON1);
	
	protected int x, y;
	protected Sprite sprite;
	protected Font font;
	protected Color textColor;
	protected String text = "";
	protected int textX, textY;
	protected boolean isTextCentered = false;
	
	public UIButton_TextSprite(int x, int y, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public UIButton_TextSprite(int x, int y, Sprite sprite, Font font, Color textColor, String text, int textX, int textY, boolean isTextCentered){
		this(x, y, sprite);
		this.font = font;
		this.textColor = textColor;
		this.text = text;
		this.textX = textX;
		this.textY = textY;
		this.isTextCentered = isTextCentered;
	}
	
	public boolean containsPoint(Point p){
		int w = sprite.getWidth()/2;
		int h = sprite.getHeight()/2;
		return (p.x > x - w && p.x < x + w && p.y > y - h && p.y < y + h);
	}
	
	public void draw(Graphics2D g) {
		if(sprite != null){
			g.translate(x, y);
			sprite.draw(g);
			if(font != null && !text.isEmpty()){
				g.setFont(font);
				g.setColor(textColor);
				if(isTextCentered){
					Util.drawStringCenteredXY(text, textX, textY, g);
				} else {
					g.drawString(text, textX, textY);
				}
			}
			g.translate(-x, -y);
		}
	}
	
	public String getText(){
		return text;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	@Override
	public void step(double tpf) {
		super.step(tpf);
		sprite.step(tpf);
	}
	
	protected abstract void onClick(boolean draggedOver);
	
	protected abstract void onDrag();
	
	protected abstract void onEnter();
	
	protected abstract void onHover();
	
	protected abstract void onLeave();
	
	protected abstract void onRelease(boolean releasedOverThis);
	
}