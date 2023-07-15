package exampleGame;
import polyEngine.*;

class Barrel extends Actor {
	public static final CollisionGroup COLLISION_GROUP = (group) -> group == Bullet.COLLISION_GROUP;

	int hp = 10;

	public Barrel(float x, float y, float rotation, Sprite sprite, Poly2D mesh,	boolean isSolid) {
		super(x, y, rotation, sprite, mesh, isSolid);
		collisionGroup = COLLISION_GROUP;
	}
	
	@Override
	public void step(double tpf) {
		super.step(tpf);
		if(hp <= 0){
			PolyEngine.remove(this);
			Scene1.score ++;
		}
	}
	
	public void hit(int strength){
		hp -= strength;
	}
}