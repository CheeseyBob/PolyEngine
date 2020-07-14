package realtimeEngine;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


final class RGWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public static int width = 800, height = 600;
	public static String title = "Game";

	RGWindow(){
		setSize(width, height);
	}
	
	void create(String title, int w, int h, boolean fullscreen){
		RGWindow.title = title;
		create(w, h, fullscreen);
	}

	void create(int w, int h, boolean fullscreen){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = w;
		height = h;
		setUndecorated(fullscreen);
		if(fullscreen){
			setLocation(0, 0);
			setSize(screenSize);
		} else {
			setSize(RGWindow.width, RGWindow.height);
			setLocation((int)(screenSize.getWidth() - RGWindow.width)/2, (int)(screenSize.getHeight() - RGWindow.height)/2);
		}
		setResizable(false);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusTraversalKeysEnabled(false);// This prevents the tab KeyEvent from being consumed before we can read it.
		setVisible(true);
		createBuffers(this);
		addKeyListener(RGSystem.input);
		addMouseListener(RGSystem.input);
		addMouseWheelListener(RGSystem.input);
	}

	private static void createBuffers(RGWindow window){
		boolean repeat = true;
		while(repeat){
			try {
				repeat = false;
				window.createBufferStrategy(2);
			} catch(IllegalStateException e){
				System.out.println("TRYCATCH#1:RGWINDOW");
				System.out.println("CAUGHT ERROR: "+e.getMessage());
				repeat = true;
			}
		}
	}
}