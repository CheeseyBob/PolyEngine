package exampleGame;
import java.awt.Color;
import java.awt.Graphics2D;

import audio.SoundHandler;

import polyEngine.*;

class Bullet extends Actor {
	double dx, dy;
	double timeToDie = 0.25f;

	public Bullet(double x, double y, double velocity, double rotation) {
		super(x, y, rotation, null, null, false);
		dx = velocity*Math.cos(rotation);
		dy = velocity*Math.sin(rotation);
		setCollisionMesh(new Poly2D(new double[]{x, x + dx/60}, new double[]{y, y + dy/60}));
	}
	
	@Override
	public void step(double tpf) {
		move(dx*tpf, dy*tpf);
		timeToDie -= tpf;
		if(timeToDie <= 0){
			PolyEngine.remove(this);
		} else {
			isSolid = true;
			Collidable collider = getCollision();
			isSolid = false;
			if(collider != null){
				if(collider instanceof Barrel){
					((Barrel)collider).hit(3);
				}
				SoundHandler.playSound(Sounds.shot2);
				PolyEngine.remove(this);
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.black);
		g.drawLine((int)getX(), (int)getY(), (int)(getX()+dx/60), (int)(getY()+dy/60));
	}
}