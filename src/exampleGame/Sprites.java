package exampleGame;
import polyEngine.Sprite;

import files.ImageHandler;

class Sprites {
	public static Sprite apple = new Sprite(ImageHandler.loadImage("img/apple.png"));
	public static Sprite barrel = new Sprite(ImageHandler.loadImage("img/barrel.png"));
	public static Sprite campfire = new Sprite(ImageHandler.loadImage("img/campfire_2x2.png"), 2, 2, 0.1);
	public static Sprite button = new Sprite(ImageHandler.loadImage("img/button.png"));
}