package polyEngine;

/**
 * A {@link CollisionGroup} defines whether a {@link Collidable} can collide with another (regardless of whether they
 * are actually colliding).
 */
public interface CollisionGroup {
	CollisionGroup ALL = (group) -> true;
	CollisionGroup NONE = (group) -> false;

	/**
	 * Whether a {@link Collidable} that has this collision group is able to collide with one in the given group.
	 * Must return {@code true} when {@code group == CollisionGroup.ALL} and return {@code false} when
	 * {@code group == CollisionGroup.NONE}.
	 */
	boolean collidesWith(CollisionGroup group);

}