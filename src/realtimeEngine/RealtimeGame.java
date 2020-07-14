package realtimeEngine;
import java.awt.Graphics2D;

public interface RealtimeGame {
   
   public void loadGame();
   
   public void step(double tpf);
   
   public void draw(Graphics2D g);
   
}