package realtimeEngine;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public final class RGSystem {
	static RGRunner runner = new RGRunner();
	static RGInput input = new RGInput();
	static RGWindow window = new RGWindow();
	static RealtimeGame game;


	/**
	 * Returns an array containing all the registered controls.
	 * @return the list of controls
	 */
	public static RGControl[] getControlList(){
		RGControl[] controlList = new RGControl[RGInput.controlList.size()];
		int i = 0;
		for(RGControl control: RGInput.controlList){
			controlList[i] = control;
			i ++;
		}
		return controlList;
	}

	/**
	 * Returns the current frame rate of the game.
	 * @return the current fps
	 */
	public static long getFPS(){
		return RGRunner.getFPS();
	}
	
	/**
	 * Returns the window this game is running in.
	 */
	public static JFrame getWindow(){
		return window;
	}

	/**
	 * Loads an image from the specified file.
	 * @return the loaded image
	 */
	public static BufferedImage loadImage(String filename){
		try {
			return ImageIO.read(new File(filename));
		} catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates the game window and runs the game.
	 */
	public static void startup(RealtimeGame game, String windowTitle, int width, int height, boolean fullscreen){
		window.create(windowTitle, width, height, fullscreen);
		RGSystem.game = game;
		game.loadGame();
		Thread thread = new Thread(runner);
		thread.start();
	}

	static void step(double tpf){
		RGInput.updateMouse(window.getMousePosition());
		game.step(tpf);
		BufferStrategy bs = window.getBufferStrategy();

		Graphics2D g = null;
		try {
			g = (Graphics2D) bs.getDrawGraphics();
		} catch(IllegalStateException e){
			System.out.println("TRYCATCH#2:RGSYSTEM");
			System.out.println("CAUGHT ERROR: "+e.getMessage());
			window.createBufferStrategy(2);
		} 
		if(g != null){
			game.draw(g);
			g.dispose();
			bs.show();
		} else try {
			Thread.sleep(100);
		} catch(InterruptedException e){

		}
	}
	
	public static void setWindowResizable(boolean resizable){
		window.setResizable(resizable);
	}
	
	public static void setWindowSize(int width, int height){
		window.setSize(width, height);
	}
	
	public static void setWindowTitle(String title){
		window.setTitle(title);
	}
	
	/**
	 * Sets the image to display for the cursor on this window.
	 * @param image
	 * @param hotspotX
	 * @param hotspotY
	 */
	public static void setCursor(BufferedImage image, int hotspotX, int hotspotY){
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(hotspotX, hotspotY), "");
		window.setCursor(cursor);
	}
	
	public static void setDesiredFPS(int fps) {
		RGRunner.desiredFrameTimeNanos = (long)(1e9/fps);
	}

	/**
	 * Returns the current width of the game window.
	 * @return the window width
	 */
	public static int winW(){
		return window.getWidth();
	}

	/**
	 * Returns the current height of the game window.
	 * @return the window height
	 */
	public static int winH(){
		return window.getHeight();
	}
}