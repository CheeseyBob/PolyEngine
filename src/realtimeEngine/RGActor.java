package realtimeEngine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class RGActor extends Point2D {
   private static int highlightSize = 10;
   
   protected BufferedImage image = null;
   private int imX, imY;
   protected double x, y, r;
   protected double angle;
   private boolean selected;
   private boolean alive;
   
   public RGActor(){
      
   }
   
   public void step(double tpf){
      return;
   }
   
   public final void draw(Graphics2D g){
      g.rotate(angle, x, y);
      drawObject(g);
      g.rotate(-angle, x, y);
      if(selected){
         g.setColor(Color.yellow);
         drawSelection(g);
      }
   }
   
   protected void drawObject(Graphics2D g){
      g.drawImage(image, null, imX, imY);
   }
   
   protected void drawSelection(Graphics2D g){
      int x1 = (int)(x - r - highlightSize);
      int y1 = (int)(y - r - highlightSize);
      int w = (int)(2*(r + highlightSize));
      g.drawOval(x1, y1, w, w);
   }
   
   public void drawDescription(Graphics2D g){
      g.setColor(Color.white);
      g.drawString("Actor", 0, 0);
   }
   
   public final void command(Point2D p, boolean playerCommand){
      if(p != null){
         if(isPlayerControllable() || !playerCommand){
            command(p);
         }
      }
   }
   
   protected void command(Point2D p){
      return;
   }
   
   public boolean isPlayerControllable(){
      return false;
   }
   
   public void setImage(BufferedImage image){
      this.image = image;
      setImageLocation();
   }
   
   private void setImageLocation(){
      if(image != null){
         imX = (int)(x - image.getWidth()/2);
         imY = (int)(y - image.getHeight()/2);
      }
   }
   
   public double getX() {
      return x;
   }
   
   public double getY() {
      return y;
   }
   
   public double getR() {
      return r;
   }
   
   public double getAngle() {
      return angle;
   }
   
   public double getAngleTo(Point2D p){
      return Math.atan2(p.getY() - y, p.getX() - x);
   }
   
   public boolean isSelected(){
      return selected;
   }
   
   public void setSelected(boolean selected){
      this.selected = selected;
   }
   
   public void setLocation(double x, double y) {
      this.x = x;
      this.y = y;
      setImageLocation();
   }
   
   public void setAngle(double angle) {
      this.angle = angle % (2*Math.PI);
   }
   
   public void lookAt(Point2D p){
      angle = getAngleTo(p);
   }
   
   public void move(double dx, double dy){
      this.x += dx;
      this.y += dy;
      setImageLocation();
   }
   
   public void rotate(double angle){
      this.angle = (this.angle + angle) % (2*Math.PI);
   }
   
   public boolean intersects(Point2D p){
      return (distanceSq(p) < r*r);
   }
   
   public boolean isAlive(){
      return alive;
   }
   
   public void setAlive(boolean aliveState){
      alive = aliveState;
      if(!alive){
         kill();
      }
   }
   
   public void kill(){
      alive = false;
   }
}