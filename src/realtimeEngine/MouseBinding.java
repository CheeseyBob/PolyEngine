package realtimeEngine;

public class MouseBinding extends Binding {
   public int button;
   
   MouseBinding(int button){
      this.button = button;
   }
   
   void pressMouse(int button) {
      if(this.button == button){
         pressed = true;
      }
   }
   
   void releaseMouse(int button) {
      if(this.button == button){
         pressed = false;
      }
   }
}