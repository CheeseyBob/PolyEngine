package polyEngine;

public interface Collidable {
	
	@Deprecated
	public abstract boolean isSolid();
	
	public abstract CollisionGroup getCollisionGroup();
	
	public abstract boolean hasCollisionMesh();
	
	public abstract Poly2D getCollisionMesh();
	
	public abstract boolean isColliding();
	
	public abstract Collidable getCollision();
	
}