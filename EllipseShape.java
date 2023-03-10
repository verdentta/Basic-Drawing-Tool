import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/*
* Component that draws an ellipse based on mouse position
* 
* Note that this program is written as the hint and is not a working program. It should be
* completed and then should be tested. You can write your own program from the scratch if 
* it is easier for you.
*/
public class EllipseShape extends Shape {

 private int xCoord, yCoord;
 private final int RADIUS = 20;
 private Color shapeColor;

 /**
  * Constructor to create an ellipse based on the mouse coordinates given from
  * the drawPanel
  */
 public EllipseShape(String color, int x, int y) {
  super(color, x, y);
  xCoord = x;
  yCoord = y;
  if (color.equalsIgnoreCase("red")) {
   shapeColor = new Color(255,0,0); // Red color value, Green value, Blue Value. Ints from 0-255 
  }
  else if (color.equalsIgnoreCase("green")) {
   shapeColor = new Color(0,255,0); // Red color value, Green value, Blue Value. Ints from 0-255 
  }
  else if (color.equalsIgnoreCase("blue")) {
      shapeColor = new Color(0,0,255); // Red color value, Green value, Blue Value. Ints from 0-255 
  }
  else if(color.equalsIgnoreCase("orange"))
      {
	  shapeColor = new Color(255,165,0);
      }
  else if (color.equalsIgnoreCase("yellow"))
      {
	  shapeColor = new Color(255, 255, 0);
      }
  else if (color.equalsIgnoreCase("pink"))
      {
	  shapeColor = new Color(255, 20, 147);
      }
  else if(color.equalsIgnoreCase("brown"))
      {
	  shapeColor = new Color(165,42, 42);
      }
   else if(color.equalsIgnoreCase("orange"))
      {
	  shapeColor = new Color(255,165,0);
      }
  else if(color.equalsIgnoreCase("black"))
      {
	  shapeColor = new Color(0, 0, 0);
      }
  else if (color.equalsIgnoreCase("yellow"))
      {
	  shapeColor = new Color(255, 215, 0);
      }
  else if (color.equalsIgnoreCase("pink"))
      {
	  shapeColor = new Color(255, 20, 147);
      }
  else if(color.equalsIgnoreCase("white"))
      {
	  shapeColor = new Color(255,255, 255);
      }
 
 }
 

 /**
  * Returns the shape's name
  */
 public String getShapeName() {
  String name = "Ellipse";
  return name;
 }

 /**
  * Draw an ellipse
  */
 public void draw(Graphics2D g2) {
  g2.setColor(shapeColor);
  g2.fillOval(xCoord, yCoord, RADIUS * 2, RADIUS);
 }

}
