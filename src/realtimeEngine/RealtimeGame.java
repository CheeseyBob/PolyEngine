package realtimeEngine;
import java.awt.Graphics2D;

public interface RealtimeGame {
	   
   public void draw(Graphics2D g);
   
   public void loadGame();
   
   public void step(double tpf);
   
}