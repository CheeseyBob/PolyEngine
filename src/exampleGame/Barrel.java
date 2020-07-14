package exampleGame;
import polyEngine.Actor;
import polyEngine.Poly2D;
import polyEngine.PolyEngine;
import polyEngine.Sprite;

class Barrel extends Actor {
	int hp = 10;

	public Barrel(float x, float y, float rotation, Sprite sprite, Poly2D mesh,	boolean isSolid) {
		super(x, y, rotation, sprite, mesh, isSolid);
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