

import javax.swing.JFrame;

/*
 * This program enters a shape's name, colour, and the location it was drawn into a simple database. The shapes can be retrieved
 * from the simple database if the drawing canvas is cleared
 * 
 * Note that this program is written as the hint and is not a working program. It should be
 * completed and then should be tested. You can write your own program from the scratch if 
 * it is easier for you.
 */

public class SimpleAssignmentViewer {

 public static void main(String[] args) {
  SimpleDatabase simpleDb = new SimpleDatabase();
  JFrame frame = new SimpleAssignment(simpleDb);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setVisible(true);
 }
 
}
