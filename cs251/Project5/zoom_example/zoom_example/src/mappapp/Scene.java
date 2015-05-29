/* Class name: Scene
 * 
 * Written by Guntas Grewal and Mihir Jham
 * 
 * Interface created to use with MapScene, the processor class.
 */
package mappapp;

import java.awt.Graphics2D;
import javax.swing.event.ChangeListener;

public interface Scene {
  void draw(Graphics2D g);
  int getWidth();
  int getHeight();
  void addChangeListener(ChangeListener listener);
}
