package exampleGame;
import java.awt.Color;
import java.awt.Font;

import audio.SoundHandler;

import polyEngine.*;

class UIButton_StartGame extends UIButton_TextSprite {

	public UIButton_StartGame(int x, int y, Sprite sprite){
		super(x, y, sprite);
	}
	
	public UIButton_StartGame(int x, int y, Sprite sprite, Font font, String text, int textX, int textY, boolean isTextCentered){
		super(x, y, sprite, font, Color.white, text, textX, textY, isTextCentered);
	}
	
	public float getZDepth() {
		return 0.0f;
	}
	
	protected void onClick(boolean draggedOver) {
	}
	
	protected void onDrag() {
	}
	
	protected void onRelease(boolean releasedOverThis) {
		if(releasedOverThis){
			SoundHandler.playSound(Sounds.shot1);
			PolyEngine.loadScene(ExampleGame.scene1, true);
		}
	}
	
	protected void onEnter() {
	}
	
	protected void onHover() {
	}
	
	protected void onLeave() {
	}
}