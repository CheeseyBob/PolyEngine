package polyEngine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

import files.FileUtils;
import maths.M;

import realtimeEngine.RGControl;
import realtimeEngine.RealtimeGame;
import realtimeEngine.RGSystem;

public class PolyEngine {
	public static final short ANTIALIASING_NONE = 0, ANTIALIASING_NEARESTNEIGHBOUR = 1, ANTIALIASING_BILINEAR = 2, ANTIALIASING_BICUBIC = 3;

	private static final LinkedList<Collidable> collideList = new LinkedList<>();
	private static LinkedList<Drawable> drawList = new LinkedList<>();
	private static LinkedList<InterfaceElement> interfaceList = new LinkedList<>();
	private static final LinkedList<Opaque> opaqueList = new LinkedList<>();
	private static final LinkedList<Clickable> selectableList = new LinkedList<>();
	private static final LinkedList<Stepable> stepList = new LinkedList<>();

	private static final LinkedList<Object> objectList = new LinkedList<>();
	private static final LinkedList<Object> placeList = new LinkedList<>();
	private static final LinkedList<Object> removeList = new LinkedList<>();

	private static Scene currentScene = null;

	public static short antialiasingType = 0;    //0 = OFF; 1 = NEAREST_NEIGHBOUR; 2 = BILINEAR; 3 = BICUBIC;
	public static boolean clearScreenBeforeDraw = true;
	public static boolean drawLOS = true;
	public static boolean drawSceneAfterObjects = false;
	public static boolean drawWireframe = false;
	public static boolean stepSceneAfterObjects = false;

	public static Color backgroundColor = Color.lightGray;

	public static double viewX = 0, viewY = 0, viewAngle = 0;
	public static double scale = 1.0f;

	public static Stream<Collidable> calculateCollisions(Collidable object) {
		// TODO - Potential optimization could be having a separate collideList for each CollisionGroup.
		return collideList.stream()
				.filter(other -> object.getCollisionGroup().collidesWith(other.getCollisionGroup()))
				.filter(other -> object.getCollisionMesh().isCollidingWith(other.getCollisionMesh()));
	}

	public static void clearScene(){
		interfaceList.clear();
		drawList.clear();
		stepList.clear();
		selectableList.clear();
		collideList.clear();
		opaqueList.clear();
		objectList.clear();
		placeList.clear();
		removeList.clear();
	}

	public static void draw(Graphics2D g) {
		g.setBackground(backgroundColor);
		if(clearScreenBeforeDraw){
			g.clearRect(0, 0, RGSystem.winW(), RGSystem.winH());
		}
		setAntialiasing(g);
		g.translate(RGSystem.winW()/2, RGSystem.winH()/2);
		g.scale(scale, scale);
		g.rotate(-viewAngle);
		g.translate(-viewX, -viewY);

		if(drawSceneAfterObjects){
			drawObjects(g);
			drawScene(g);
		} else {
			drawScene(g);
			drawObjects(g);
		}

		if(drawLOS){
			g.setColor(Color.black);
			double obscureDistance = (RGSystem.winW() + RGSystem.winH()) / 2.0;
			for(Opaque opaque: opaqueList){
				Poly2D mesh = opaque.getOpacityMesh();
				if(mesh != null){
					mesh.obscureView(viewX, viewY, obscureDistance, g);
				}
			}
		}

		if(drawWireframe){
			g.setColor(Color.yellow);
			for(Collidable collidable: collideList){
				Poly2D mesh = collidable.getCollisionMesh();
				if(mesh != null){
					mesh.draw(g);
				}
			}
		}

		g.translate(viewX, viewY);
		g.rotate(viewAngle);
		g.scale(1.0/scale, 1.0/scale);
		g.translate(-RGSystem.winW()/2, -RGSystem.winH()/2);

		for(InterfaceElement interfaceElement: interfaceList){
			interfaceElement.draw(g);
		}
	}

	private static void drawObjects(Graphics2D g){
		for(Drawable drawable: drawList){
			drawable.draw(g);
		}
	}

	private static void drawScene(Graphics2D g){
		if(currentScene != null){
			currentScene.draw(g);
		}
	}

	public static long getFPS(){
		return RGSystem.getFPS();
	}

	public static Scene getLastLoadedScene(){
		return currentScene;
	}

	public static Point getMouseScreenPosition(){
		return RGControl.mousePos();
	}

	/**
	 * @return where the cursor is pointing in the game space.
	 */
	public static Point2D getMouseWorldPosition(){
		Point2D.Double mousePosition = new Point2D.Double(RGControl.mousePos().x, RGControl.mousePos().y);
		mousePosition.x -= RGSystem.winW()/2;
		mousePosition.y -= RGSystem.winH()/2;
		mousePosition.x /= scale;
		mousePosition.y /= scale;
		mousePosition = M.rotate(mousePosition, viewAngle);
		mousePosition.x += viewX;
		mousePosition.y += viewY;
		return mousePosition;
	}

	public static LinkedList<Object> getObjectList(){
		return new LinkedList<>(objectList);
	}

	public static LinkedList<Clickable> getSelectedObjectList(){
		LinkedList<Clickable> list = new LinkedList<Clickable>();
		for(Clickable object : selectableList){
			if(object.containsPoint(getMouseWorldPosition())){
				list.add(object);
			}
		}
		return list;
	}

	public static int getWindowHeight(){
		return RGSystem.winH();
	}

	public static int getWindowWidth(){
		return RGSystem.winW();
	}

	/**
	 * Sets the lastLoadedScene to the given scene, and places all objects in scene.getObjectList().
	 */
	public static void loadScene(Scene scene, boolean clearPrevious){
		if(clearPrevious){
			clearScene();
		}
		for(Object object: scene.getObjectList()){
			place(object);
		}
		currentScene = scene;
		scene.load();
	}

	public static void moveView(double dx, double dy){
		viewX += dx;
		viewY += dy;
	}

	public static void place(Object object){
		placeList.add(object);
		if(object instanceof OnPlace){
			((OnPlace) object).onPlace();
		}
	}

	public static void placeAll(Collection<?> collection){
		collection.forEach(PolyEngine::place);
	}

	public static void remove(Object object){
		removeList.add(object);
		if(object instanceof OnPlace){
			((OnPlace) object).onRemove();
		}
	}

	public static void removeAll(Collection<?> collection){
		collection.forEach(PolyEngine::remove);
	}

	public static void resortZDepths(){
		LinkedList<Drawable> newDrawList = new LinkedList<Drawable>();
		// TODO - Test this: drawList.sort(Comparator.comparingDouble(Drawable::getZDepth));
		for(Drawable drawable: drawList){
			int index = 0;
			for(Drawable reorderedDrawable: newDrawList){
				if(reorderedDrawable.getZDepth() > drawable.getZDepth()){
					break;
				} else {
					index ++;
				}
			}
			newDrawList.add(index, drawable);
		}
		drawList = newDrawList;

		// Also resort interface elements. //
		LinkedList<InterfaceElement> newInterfaceList = new LinkedList<InterfaceElement>();
		for(InterfaceElement interfaceElement: interfaceList){
			int index = 0;
			for(InterfaceElement reorderedElement: newInterfaceList){
				if(reorderedElement.getZDepth() > interfaceElement.getZDepth()){
					break;
				} else {
					index ++;
				}
			}
			newInterfaceList.add(index, interfaceElement);
		}
		interfaceList = newInterfaceList;
	}

	private static void setAntialiasing(Graphics2D g){
		switch(antialiasingType){
			case 0:
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				break;
			case 1:
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			case 2:
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				break;
			case 3:
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				break;
		}
	}

	public static void setCursor(BufferedImage image, int hotspotX, int hotspotY){
		RGSystem.setCursor(image, hotspotX, hotspotY);
	}

	public static void setDesiredFPS(int fps) {
		RGSystem.setDesiredFPS(fps);
	}

	public static void setViewLocation(double x, double y) {
		viewX = x;
		viewY = y;
	}

	public static void setViewLocation(Point2D location) {
		viewX = location.getX();
		viewY = location.getY();
	}

	public static void setViewPosition(Point2D location, double angle) {
		viewX = location.getX();
		viewY = location.getY();
		viewAngle = angle;
	}

	public static void setViewPosition(double x, double y, double angle) {
		viewX = x;
		viewY = y;
		viewAngle = angle;
	}

	public static void setWindowTitle(String title){
		RGSystem.setWindowTitle(title);
	}

	public static void startup(RealtimeGame game, String windowTitle, int width, int height, boolean fullscreen, ControlList controlList){
		FileUtils.setExecutionPath(game); // Set the execution path to ensure we load assets from the correct directory.
		RGSystem.startup(game, windowTitle, width, height, fullscreen);
		controlList.setupControls();
	}

	public static void step(double tpf) {
		if(stepSceneAfterObjects){
			stepObjects(tpf);
			stepScene(tpf);
		} else {
			stepScene(tpf);
			stepObjects(tpf);
		}
		while(!removeList.isEmpty()) {
			Object object = removeList.remove();
			objectList.remove(object);
			if(object instanceof InterfaceElement){
				interfaceList.remove(object);
			}
			if(object instanceof Drawable){
				drawList.remove(object);
			}
			if(object instanceof Collidable){
				collideList.remove(object);
			}
			if(object instanceof Opaque){
				opaqueList.remove(object);
			}
			if(object instanceof Stepable){
				stepList.remove(object);
			}
			if(object instanceof Clickable){
				selectableList.remove(object);
			}
		}
		while(!placeList.isEmpty()){
			Object object = placeList.remove();
			objectList.add(object);
			if(object instanceof InterfaceElement){
				interfaceList.add((InterfaceElement)object);
			}
			if(object instanceof Drawable drawableObject){
				int index = 0;
				for(Drawable drawable: drawList){
					if(drawable.getZDepth() > drawableObject.getZDepth()){
						break;
					} else {
						index ++;
					}
				}
				drawList.add(index, drawableObject);
			}
			if(object instanceof Collidable){
				collideList.add((Collidable)object);
			}
			if(object instanceof Opaque){
				opaqueList.add((Opaque)object);
			}
			if(object instanceof Stepable){
				stepList.add((Stepable)object);
			}
			if(object instanceof Clickable){
				selectableList.add((Clickable)object);
			}
		}
	}

	private static void stepObjects(double tpf){
		for(Stepable stepable: stepList){
			stepable.step(tpf);
		}
	}

	private static void stepScene(double tpf){
		if(currentScene != null){
			currentScene.step(tpf);
		}
	}
}