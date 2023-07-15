package polyEngine;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import maths.M;

public class Actor implements Location, Stepable, Drawable, Collidable, Opaque {
	protected double x = 0, y = 0, rotation = 0;
	protected double absoluteZDepth;
	protected boolean hasDynamicZDepth;
	protected Sprite sprite;
	protected Poly2D collisionMesh;
	protected Poly2D opacityMesh;
	
	public boolean isSolid;
	public CollisionGroup collisionGroup;
	
	public Actor(double x, double y, double absoluteZDepth, boolean hasDynamicZDepth, Sprite sprite){
		this(x, y, absoluteZDepth, hasDynamicZDepth, 0, sprite, null, false, CollisionGroup.NONE, null);
	}
	
	public Actor(double x, double y, double absoluteZDepth, boolean hasDynamicZDepth, double rotation, Sprite sprite, Poly2D collisionMesh, boolean isSolid, CollisionGroup collisionGroup, Poly2D opacityMesh){
		setSprite(sprite);
		setCollisionMesh(collisionMesh);
		setOpacityMesh(opacityMesh);
		setLocation(x, y);
		rotate(rotation);
		this.isSolid = isSolid;
		this.collisionGroup = collisionGroup;
		this.absoluteZDepth = absoluteZDepth;
		this.hasDynamicZDepth = hasDynamicZDepth;
	}
	
	public Actor(double x, double y, double rotation, Sprite sprite, Poly2D collisionMesh, boolean isSolid){
		this(x, y, 0.0f, false, rotation, sprite, collisionMesh, isSolid, CollisionGroup.NONE, null);
	}
	
	public double distanceTo(Actor actor){
		return Point2D.distance(this.x, this.y, actor.x, actor.y);
	}
	
	public double distanceTo(Point2D point){
		return Point2D.distance(this.x, this.y, point.getX(), point.getY());
	}
	
	public double distanceTo(double x, double y){
		return Point2D.distance(this.x, this.y, x, y);
	}
	
	public void draw(Graphics2D g) {
		if(sprite != null){
			int xInt = (int)x;
			int yInt = (int)y;
			g.translate(xInt, yInt);
			g.rotate(rotation);
			sprite.draw(g);
			g.rotate(-rotation);
			g.translate(-xInt, -yInt);
		}
	}
	
	public CollisionGroup getCollisionGroup(){
		return collisionGroup;
	}
	
	public Poly2D getCollisionMesh(){
		return collisionMesh;
	}
	
	public Point2D getLocation(){
		return new Point2D.Double(x, y);
	}
	
	public Poly2D getOpacityMesh(){
		return opacityMesh;
	}
	
	public double getRotation(){
		return rotation;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZDepth(){
		if(hasDynamicZDepth){
			return y - PolyEngine.viewY + absoluteZDepth;
		} else {
			return absoluteZDepth;
		}
	}

	private boolean isObstructed(){
		return isSolid && PolyEngine.calculateCollisions(this)
				.anyMatch(collider -> collider != this);
	}
	
	public void move(double dx, double dy){
		if(collisionMesh == null || !isSolid){
			setLocation(x + dx, y + dy);
		} else {
			setLocation(x + dx, y + dy);
			if(isObstructed()){
				setLocation(x - dx, y - dy);
			}
		}
	}
	
	public void rotate(double angle){
		if(collisionMesh == null || !isSolid){
			setRotation(this.rotation + angle);
		} else {
			setRotation(this.rotation + angle);
			if(isObstructed()){
				setRotation(this.rotation - angle);
			}
		}
	}
	
	public void setCollisionMesh(Poly2D mesh){
		if(mesh != null){
			this.collisionMesh = mesh.clone();
		} else {
			this.collisionMesh = null;
		}
	}
	
	public void setLocation(double x, double y){
		if(collisionMesh != null){
			collisionMesh.translate(x - this.x, y - this.y);
		}
		if(opacityMesh != null){
			opacityMesh.translate(x - this.x, y - this.y);
		}
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(Point2D location){
		setLocation((float)location.getX(), (float)location.getY());
	}
	
	public void setOpacityMesh(Poly2D mesh){
		if(mesh != null){
			this.opacityMesh = mesh.clone();
		} else {
			this.opacityMesh = null;
		}
	}
	
	public void setRotation(double rotation){
		if(collisionMesh != null){
			collisionMesh.rotate(rotation - this.rotation);
		}
		if(opacityMesh != null){
			opacityMesh.rotate(rotation - this.rotation);
		}
		this.rotation = (float) M.wrapAngle(rotation);
	}
	
	public void setSprite(Sprite sprite){
		if(sprite != null){
			this.sprite = sprite.clone();
		} else {
			this.sprite = null;
		}
	}
	
	public void setZDepth(double absoluteZDepth, boolean hasDynamicZDepth){
		this.absoluteZDepth = absoluteZDepth;
		this.hasDynamicZDepth = hasDynamicZDepth;
	}
	
	public void step(double tpf){
		if(sprite != null){
			sprite.step(tpf);
		}
	}
}