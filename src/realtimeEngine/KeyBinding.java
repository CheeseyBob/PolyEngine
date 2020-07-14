package realtimeEngine;

public class KeyBinding extends Binding {
   public int keyCode;
   
   KeyBinding(int keyCode){
      this.keyCode = keyCode;
   }
   
   void pressKey(int keyCode) {
      if(this.keyCode == keyCode){
         pressed = true;
      }
   }
   
   void releaseKey(int keyCode) {
      if(this.keyCode == keyCode){
         pressed = false;
      }
   }
}