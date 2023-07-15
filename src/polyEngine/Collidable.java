package polyEngine;

/**
 * Signals that an object is to be registered for collision checking when placed in {@link PolyEngine}.
 */
public interface Collidable {

	/**
	 * The collision group to be used when determining which other objects could potentially collide with this one.
	 * Must be non-null (consider the trivial default {@link CollisionGroup} {@code NONE} instead).
	 */
	CollisionGroup getCollisionGroup();

	/**
	 * The polygon to be used for checking collisions with this object. Must be non-null unless the
	 * {@link CollisionGroup} is {@code ALL} or {@code NONE}.
	 */
	Poly2D getCollisionMesh();
	
}