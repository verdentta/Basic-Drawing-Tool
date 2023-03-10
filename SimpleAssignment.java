

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

/*
 * A frame for the GUI of the program to draw and store shapes
 * 
 * Note that this program is written as the hint and is not a working program. It should be
 * completed and then should be tested. You can write your own program from the scratch if 
 * it is easier for you.
 */

public class SimpleAssignment extends JFrame {

 private static final int FRAME_WIDTH = 1500;
 private static final int FRAME_HEIGHT = 900;

 private JLabel enterColourLabel;
 private JTextField enterColourField;
 private JButton clearButton;
 private JButton removeDataButton;
 private JButton getDataButton;
 private JButton ellipseButton;
 private JButton rectangleButton;
 private JButton setColourButton;
 private TitledBorder dbTitle;
 private TitledBorder resultsTitle;
 private TitledBorder initializationTitle;
 private TitledBorder commandsTitle;
 private TitledBorder enterDbTitle;
 private DefaultListModel<String> dataStored; // list on left
 private JList<String> dataList; // list on left
 private Border blackBorder;
 private SimpleDatabase simpleDb;
 private int indexToRemoveData;
 private boolean ellipseSelected;
 private boolean rectangleSelected;
 private String colourName;
 public DrawPanel toDrawPanel;

 /*
  * Constructor for SimpleAssignment
  */
 public SimpleAssignment(SimpleDatabase simpleDb) {
  // Initialize all the member/instance variables

  dataList = new JList();
  this.simpleDb = simpleDb;
  int indexToRemoveData = 0;
  toDrawPanel = new DrawPanel();
  dataStored = new DefaultListModel<String>();
  blackBorder = BorderFactory.createLineBorder(Color.GREEN, 2);

  createTextField();
  createButtons();
  createList();
  createPanels();

  setSize(FRAME_WIDTH, FRAME_HEIGHT);
  this.setTitle("Simple Assignment");
  this.setResizable(false);
 }

 /*
  * Create the necessary text field with its corresponding label
  */
 private void createTextField() {
  final int FIELD_WIDTH = 10;
  enterColourLabel = new JLabel("Enter Colour:");
  enterColourField = new JTextField();
  enterColourField.setColumns(FIELD_WIDTH);
 }

 /*
  * Create all the necessary buttons
  */
 private void createButtons() {
  ellipseButton = new JButton("Ellipse");
  ellipseButton.addActionListener(new EllipseListener());

  rectangleButton = new JButton("Rectangle");
  rectangleButton.addActionListener(new RectangleListener());

  getDataButton = new JButton("Restore Data");
  getDataButton.setEnabled(false);
  getDataButton.addActionListener(new GetDataListener());

  removeDataButton = new JButton("Remove Data");
  removeDataButton.addActionListener(new RemoveDataListener());
  removeDataButton.setEnabled(false);

  setColourButton = new JButton("Set Colour");
  setColourButton.addActionListener(new SetColourListener());

  clearButton = new JButton("Clear Drawing");
  clearButton.addActionListener(new ClearListener());
 }

 /*
  * Listener for when the colour name is input into the text field
  */
 class SetColourListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   colourName = enterColourField.getText();
  }
 }

 /*
  * Listener for the button to get data from the simple database
  * 
  * Part B: Add appropriate exception handling
  */
 class GetDataListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   ArrayList<Shape> shapesRestored = simpleDb.getStoredShapes();
   System.out.println(simpleDb.getStoredShapes().size() + " get data Listener");
   // adding things to the list on the left of the window
   for (Shape s : shapesRestored) {
    dataStored
      .addElement(s.getShapeName() + " " + s.getColour() + " " + s.getXcoord() + "," + s.getYcoord());

    if (s.getShapeName().equals("Ellipse")) {
     EllipseShape a = new EllipseShape(s.getColour(), s.getXcoord(), s.getYcoord());
     simpleDb.addShape(a);

    }
    else if (s.getShapeName().equals("Rectangle")) {
     RectangleShape a = new RectangleShape(s.getColour(), s.getXcoord(), s.getYcoord());
     simpleDb.addShape(a);
    }
   }

   toDrawPanel.restoreShapes(shapesRestored); // calling the draw method to add to right of window
   getDataButton.setEnabled(false);
  }
 }

 /*
  * Listener to remove data from the simple database and remove it from the
  * drawing canvas
  * 
  * Part B: Add appropriate exception handling
  */
 class RemoveDataListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   dataStored.remove(indexToRemoveData);
   simpleDb.remove(indexToRemoveData);
   toDrawPanel.removeShape(indexToRemoveData);
  }
 }

 /*
  * Listener to check if ellipse button is clicked on
  */
 class EllipseListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   ellipseSelected = true;
   rectangleSelected = false;
  }
 }

 /*
  * Listener to check if rectangle button is clicked on
  */

 class RectangleListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   ellipseSelected = false;
   rectangleSelected = true;
  }
 }

 /*
  * Listener to clear the drawing canvas and allow the user to get data from
  * simple database afterwards
  */
 class ClearListener implements ActionListener {
  public void actionPerformed(ActionEvent event) {
   simpleDb.getStoredShapes().clear();
   dataStored.clear();
   simpleDb.store(); // stored shapes into storedShapes
   System.out.println(simpleDb.getStoredShapes().size() + "before clear");
   simpleDb.getArrayList().clear();
   System.out.println(simpleDb.getStoredShapes().size() + " after clear");
   toDrawPanel.clear();
   System.out.println(simpleDb.getStoredShapes().size() + " after drawPanel");
   getDataButton.setEnabled(true);
  }
 }

 /*
  * Create the list to show the data in the simple database
  */
 private void createList() {
  dataList = new JList<String>(dataStored);
  dataList.setVisibleRowCount(20);
  dataList.setPrototypeCellValue(String.format("%80s", ""));
  dataList.setLayoutOrientation(JList.VERTICAL);
  dataList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
  dataList.addListSelectionListener(new RemoveFromListListener());
  dataList.setBorder(blackBorder);
 }

 /*
  * Listener to check which shape is to be removed from the simple database and
  * drawing canvas
  */
 class RemoveFromListListener implements ListSelectionListener {
  public void valueChanged(ListSelectionEvent event) {
   if (event.getValueIsAdjusting() == false) {

    if (dataList.getSelectedIndex() == -1) {
     // Part A: No selection, disable remove data button
     // Part B: No selection, remove data should remain enabled
     removeDataButton.setEnabled(false);

    } else {
     // Part A: Selection, enable the remove data button.
     // Part B: Selection, remove data should have been enabled already
     indexToRemoveData = dataList.getSelectedIndex();
     removeDataButton.setEnabled(true);
    }
   }
  }
 }

 /*
  * Creates all the panels in the GUI and add to the frame
  */
 private void createPanels() {
  Border empty = BorderFactory.createEmptyBorder();

  JPanel mainPanel = new JPanel();
  JPanel centerPanel = new JPanel(new BorderLayout(20, 50));
  JPanel resultsPanel = new JPanel();
  resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
  JPanel inputDbPanel = new JPanel();
  JPanel commandsPanel = new JPanel(new GridLayout(2, 2));
  JPanel initializationsPanel = new JPanel(new GridLayout(2, 2));
  toDrawPanel = new DrawPanel();
  toDrawPanel.setBackground(new java.awt.Color(255, 255, 255));
  toDrawPanel.setBorder(blackBorder);

  JScrollPane dataPane = new JScrollPane(dataList);

  dbTitle = BorderFactory.createTitledBorder(empty, "List of Shapes");
  dbTitle.setTitleJustification(TitledBorder.CENTER);
  enterDbTitle = BorderFactory.createTitledBorder(empty, "Colour Input");
  enterDbTitle.setTitleJustification(TitledBorder.CENTER);
  initializationTitle = BorderFactory.createTitledBorder(empty, "Choose Shape");
  initializationTitle.setTitleJustification(TitledBorder.CENTER);
  commandsTitle = BorderFactory.createTitledBorder(empty, "Commands");
  commandsTitle.setTitleJustification(TitledBorder.CENTER);
  resultsTitle = BorderFactory.createTitledBorder(empty, "Draw Here");
  resultsTitle.setTitleJustification(TitledBorder.CENTER);

  dataPane.setBorder(dbTitle);
  resultsPanel.setBorder(resultsTitle);
  inputDbPanel.setBorder(enterDbTitle);
  initializationsPanel.setBorder(initializationTitle);
  commandsPanel.setBorder(commandsTitle);

  // Add components to the correct panels
  inputDbPanel.add(enterColourLabel);
  inputDbPanel.add(enterColourField);

  initializationsPanel.add(ellipseButton);
  initializationsPanel.add(rectangleButton);

  commandsPanel.add(setColourButton);
  commandsPanel.add(getDataButton);
  commandsPanel.add(removeDataButton);
  commandsPanel.add(clearButton);

  resultsPanel.add(toDrawPanel);

  centerPanel.add(inputDbPanel, BorderLayout.CENTER);
  centerPanel.add(initializationsPanel, BorderLayout.NORTH);
  centerPanel.add(commandsPanel, BorderLayout.SOUTH);

  mainPanel.add(dataPane);
  mainPanel.add(centerPanel);
  mainPanel.add(resultsPanel);

  add(mainPanel);
 }

 /*
  * An implementation of JPanel to keep track of the shapes drawn on the canvas
  */
 class DrawPanel extends JPanel {
  /*
   * Part A: ArrayList for shapesDrawn Part B: LinkedList for shapesDrawn
   */
  private ArrayList<Shape> shapesDrawn;

  /*
   * Constructor for the drawing canvas
   */
  DrawPanel() {
   setPreferredSize(new Dimension(275, 350));
   // Create ArrayList of shapes drawn for Part A, LinkedList for Part B
   shapesDrawn = new ArrayList<Shape>();

   // Add MouseListener here
   addMouseListener(new mouseListener());
  }

  /*
   * Create MouseListener for when ellipse or rectangle button is selected
   */

  class mouseListener implements MouseListener {
   /*
    * (non-Javadoc)
    * 
    * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
    */
   @Override
   public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
    int x = (int) e.getPoint().getX();
    int y = (int) e.getPoint().getY();

    if (ellipseSelected) {
     EllipseShape a = new EllipseShape(colourName, x, y);
     shapesDrawn.add(a);
     simpleDb.addShape(a);
     dataStored.addElement(
       a.getShapeName() + " " + a.getColour() + " " + a.getXcoord() + "," + a.getYcoord());
    } else if (rectangleSelected) {
     RectangleShape a = new RectangleShape(colourName, x, y);
     shapesDrawn.add(a);
     simpleDb.addShape(a);
     dataStored.addElement(
       a.getShapeName() + " " + a.getColour() + " " + a.getXcoord() + "," + a.getYcoord());

    }
    repaint();
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
    */
   @Override
   public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * 
    * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
    */
   @Override
   public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * 
    * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
    */
   @Override
   public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * 
    * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
    */
   @Override
   public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub

   }
  }

  /*
   * Restore the shapes obtained from the simple database to the drawing canvas
   * 
   * Part A: Restore an ArrayList Part B: Restore a LinkedList
   */
  public void restoreShapes(ArrayList<Shape> savedShapes) {
   for (int i = 0; i < savedShapes.size(); i++) {
    if (savedShapes.get(i).getShapeName().equals("Ellipse")) {
     EllipseShape a = new EllipseShape(savedShapes.get(i).getColour(), savedShapes.get(i).getXcoord(), savedShapes.get(i).getYcoord());
     shapesDrawn.add(a);
//     simpleDb.addShape(a);
//     dataStored.addElement(
//       a.getShapeName() + " " + a.getColour() + " " + a.getXcoord() + "," + a.getYcoord());
    }
    else if 
      (savedShapes.get(i).getShapeName().equals("Rectangle")) {
     RectangleShape a = new RectangleShape(savedShapes.get(i).getColour(), savedShapes.get(i).getXcoord(), savedShapes.get(i).getYcoord());
     shapesDrawn.add(a);
      }
//     simpleDb.addShape(a);

   //shapesDrawn = savedShapes;
   repaint();
   }
  }

  /*
   * Clear the drawing canvas of shapes
   * 
   * Part A: Clear from ArrayList Part B: Clear from LinkedList
   */

  public void clear() {
   shapesDrawn.clear();
   repaint();
  }

  /*
   * Remove a shape from the drawing canvas
   * 
   * Part A: Remove from ArrayList with given index Part B: Remove from LinkedList
   * appropriately with given index
   */
  public void removeShape(int index) {
   shapesDrawn.remove(index);
   repaint();
  }

  // Draw the shapes in the simple database on the drawing canvas
  public void paintComponent(Graphics g) {
   super.paintComponent(g);
   Graphics2D g2 = (Graphics2D) g;
   for (int i = 0; i < shapesDrawn.size(); i++) {
    shapesDrawn.get(i).draw(g2);
   }
  }
 }
}
