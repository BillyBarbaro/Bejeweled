import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A modified implementation of the game Jewel Quest
 * @author Billy Barbaro
 */

public class Jewels extends JFrame implements ActionListener {
  
  /** Number of rows in the grid */
  private int rows;
  /** Number of columns in the grid */
  private int columns;
  /** Number of different tiles in the game */
  private int numJewels;
  /** The grid of buttons on which the game is played */
  private JButton[][] buttons;
  /** Saves the row of the first selected tile in a turn */
  private int firstSelectedRow = -1;
  /** Saves the column of the first selected tile in a turn */
  private int firstSelectedColumn = -1;
  /** Counts the number of moves the player has taken */
  private int moveCount = 0;
  
  /** Constructor that creates the board and begins a new game
    * @param rows  the number of rows to be in the grid (will be lowered to 8 if any greater)
    * @param columns  the number of columns to be in the grid (will be lowered to 14 if any greater)
    * @param numJewels  the number of different pieces to be matched (will be lowered to 8 if any greater)
    */
  public Jewels(int rows, int columns, int numJewels) {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {
    }
    
    if (rows > 8)
      rows = 8;
    if (rows < 1)
      rows = 1;
    if (columns > 14)
      columns = 14;
    if (columns < 1)
      columns = 1;
    if(numJewels > 8)
      numJewels = 8;
    if (numJewels < 2)
      numJewels = 2;
    
    this.rows = rows;
    this.columns = columns;
    this.numJewels = numJewels;
    this.setSize(columns * 100, rows * 100);
    
    // Creates the JPanel to be used in the game
    JPanel board = new JPanel(new GridLayout(rows, columns));
    
    buttons = new JButton[rows][columns];
    
    // An array of colors to assign to the grid
    Color colors[] = {Color.green, Color.blue, Color.red, Color.yellow, Color.orange, Color.magenta, Color.pink, Color.white};
    
    // Nested loops iterate through the array of buttons initializing them, adding their action listeners, and setting their properties
    for (int i = 0; i < rows; i++) {
      for(int j = 0; j < columns; j++) {
        buttons[i][j] = new JButton();
        buttons[i][j].addActionListener(this);
        buttons[i][j].setFocusPainted(false);
        buttons[i][j].setBackground(colors[(int)(Math.random() * numJewels)]);
        board.add(buttons[i][j]);
      }
    }
    
    this.getContentPane().add(board, "Center");
    
    
    setVisible(true);
  }
  
  /** Constructor that creates a 8 x 10 board with 4 colors by calling the other constructor
    */
  public Jewels() {
    this(8, 10, 4);
  }
  
  /** Method called when a button is clicked
    * @param e adress of the object that generated the action event
    */
  public void actionPerformed(ActionEvent e) {
    
    // The clicked button
    JButton b = (JButton) e.getSource();
    
    // Nested loops check over all the buttons to determine the index of the button pressed
    for (int i = 0; i < rows; i++) {
      for(int j = 0; j < columns; j++) {
        if(b == buttons[i][j]) {
          if (firstSelectedRow == -1)
            firstSelect(i, j, buttons);
          else
            secondSelect(i, j, buttons);
        }
      }
    }
  }
  
  /** Stores the location of the first button clicked in memory
    * @param row  the row of the button that was clicked
    * @param column  the column of the button that was clicked
    * @param buttons  array of buttons that the game is played on
    */
  public void firstSelect(int row, int column, JButton[][] buttons) {
    firstSelectedRow = row;
    firstSelectedColumn = column;
  }
  
  /** Takes the second clicked button, checks to see if the switch is legal, and if it is, makes the switches and moves the grid down.
    * @param row  the row of the button that was clicked
    * @param column  the column of the button that was clicked
    * @param buttons  array of buttons that the game is played on
    */
  public void secondSelect(int row, int column, JButton[][] buttons) {
    if (spotsTouch(firstSelectedRow, firstSelectedColumn, row, column)) {
      switchJewels(firstSelectedRow, firstSelectedColumn, row, column, buttons);
      
      // Each of the below ints give the number of sqaures that match in the direction of their name
      int up = upMatch(firstSelectedRow, firstSelectedColumn, buttons);
      int down = downMatch(rows, firstSelectedRow, firstSelectedColumn, buttons);
      int left = leftMatch(firstSelectedRow, firstSelectedColumn, buttons);
      int right = rightMatch(columns, firstSelectedRow, firstSelectedColumn, buttons);
      
      if((left + right >= 2) && (up + down >= 2)) {
        markHorizontal(firstSelectedRow, firstSelectedColumn, left, right, buttons);
        markVertical(firstSelectedRow, firstSelectedColumn, up, down, buttons);
        fallVertical(firstSelectedRow, firstSelectedColumn, up, down, buttons);
        fallHorizontal(firstSelectedRow, firstSelectedColumn - 1, left - 1, 0, buttons);
        fallHorizontal(firstSelectedRow, firstSelectedColumn + 1, 0, right - 1, buttons);
        moveCount++;
        if (checkWin(buttons))
          System.out.println("You win! It took you " + moveCount + " moves!");
      }
      else if(left + right >= 2) {
        markHorizontal(firstSelectedRow, firstSelectedColumn, left, right, buttons);
        fallHorizontal(firstSelectedRow, firstSelectedColumn, left, right, buttons);
        moveCount++;
        checkWin(buttons);
        if (checkWin(buttons))
          System.out.println("You win! It took you " + moveCount + " moves!");
      }
      else if (up + down >= 2) {
        markVertical(firstSelectedRow, firstSelectedColumn, up, down, buttons);
        fallVertical(firstSelectedRow, firstSelectedColumn, up, down, buttons);
        moveCount++;
        checkWin(buttons);
        if (checkWin(buttons))
          System.out.println("You win! It took you " + moveCount + " moves!");
      }
      else {
        switchJewels(firstSelectedRow, firstSelectedColumn, row, column, buttons);
      }
    }
    
    firstSelectedRow = -1;
    firstSelectedColumn = -1;
  }
  
  /** Checks to see if the two selected tiles are adjacent
    * @param row  the row of the second button that was clicked
    * @param column  the column of the second button that was clicked
    * @return boolean  tells whether or not the rows are adjacent
    */
  public boolean spotsTouch(int firstSelectedRow, int firstSelectedColumn, int row, int column) {
    return (row == firstSelectedRow && (column - firstSelectedColumn == 1 || column - firstSelectedColumn == -1)) || (column == firstSelectedColumn && (row - firstSelectedRow == 1 || row - firstSelectedRow == -1));
  }
  
  /** Iterates upward through the grid and counts the number of matching tiles
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param buttons  the grid of buttons
    * @return int  the number of buttons in a row above the selected tile that are the same type
    */
  public int upMatch(int row, int column, JButton buttons[][]) {
    
    // The number of matches in the up direction.
    int matches = 0;
    
    // Color of the switched tile
    Color match = buttons[row][column].getBackground();
    
    row--;
    // Loops across tiles upwards until it finds one that doesn't match the correct color
    while (row >= 0 && buttons[row][column].getBackground() == match) {
      matches++;
      row--;
    }
    return matches;
  }
  
  /** Iterates downward through the grid and counts the number of matching tiles
    * @param totalRows  the total number of rows in the grid
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param buttons  the grid of buttons
    * @return int  the number of buttons in a row below the selected tile that are the same type
    */
  public int downMatch(int totalRows, int row, int column, JButton buttons[][]) {
    
    // The number of matches in the down direction.
    int matches = 0;
    
    // Color of the switched tile
    Color match = buttons[row][column].getBackground();
    
    row++;
    // Loops across tiles downwards until it finds one that doesn't match the correct color
    while (row < totalRows && buttons[row][column].getBackground() == match) {
      matches++;
      row++;
    }
    return matches;
  }
  
  /** Iterates right through the grid and counts the number of matching tiles
    * @param totalColumns  the total number of columns in the grid
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param buttons  the grid of buttons
    * @return int  the number of buttons in a row to the right of the selected tile that are the same type
    */
  public int rightMatch(int totalColumns, int row, int column, JButton buttons[][]) {
    
    // The number of matches to the right.
    int matches = 0;
    
    // Color of the switched tile
    Color match = buttons[row][column].getBackground();

    column++;
    // Loops right across tiles until it finds one that doesn't match the correct color
    while (column < totalColumns && buttons[row][column].getBackground() == match) {
      matches++;
      column++;
    }
    return matches;
  }
  
  /** Iterates left through the grid and counts the number of matching tiles
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param buttons  the grid of buttons
    * @return int  the number of buttons in a row to the left of the selected tile that are the same type
    */
   public int leftMatch(int row, int column, JButton buttons[][]) {
     
    // The number of matches to the left.
    int matches = 0;
    
    // Color of the switched tile
    Color match = buttons[row][column].getBackground();
    
    column--;
    // Loops left across tiles until it finds one that doesn't match the correct color
    while (column >= 0 && buttons[row][column].getBackground() == match) {
      matches++;
      column--;
    }
    return matches;
   }
   
   /** Marks the tiles that match horizontally
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param left  the number of matching tiles to the left of the selected tile
    * @param right the number of matching tiles to the right of the selected tile
    * @param buttons  the grid of buttons
    */
   public void markHorizontal(int row, int column, int left, int right, JButton[][] buttons) {
     
     // Marks the appropriate number of tiles in each horiztonal direction 
     for(int i = (column - left); i <= (column + right); i++) {
       buttons[row][i].setText("*");
     }
   }
   
   /** Marks the tiles that match vertically
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param up  the number of matching tiles above the selected tile
    * @param down the number of matching tiles under the selected tile
    * @param buttons  the grid of buttons
    */
   public void markVertical(int row, int column, int up, int down, JButton[][] buttons) {
     // Marks the appropriate number of tiles in each vertical direction 
     for(int i = (row - up); i <= (row + down); i++) {
       buttons[i][column].setText("*");
     }
   }
   
   /** Shifts all the tiles above the horizontal match down a row and creates a new row at the top
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param left  the number of matching tiles to the left of the selected tile
    * @param right the number of matching tiles to the right of the selected tile
    * @param buttons  the grid of buttons
    */
   public void fallHorizontal(int row, int column, int left, int right, JButton[][] buttons) {
     
     // Runs across all the columns to be shifted down
     for (int i = column - left; i <= column + right; i++) {
       // Simply a loop index
       int currentRow = row;
       
       // Shifts the blocks down the columns
       while (currentRow > 0) {
         buttons[currentRow][i].setBackground(buttons[currentRow - 1][i].getBackground());
         currentRow--;
       }
       
       // Array of possible colors for the new tiles
       Color colors[] = {Color.green, Color.blue, Color.red, Color.yellow, Color.orange, Color.magenta, Color.pink, Color.white};
       buttons[currentRow][i].setBackground(colors[(int)(Math.random() * numJewels)]);
     }
   }
   
   /** Shifts all the tiles above the vertical match down the column and creates new tiles at the top
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param up  the number of matching tiles above the selected tile
    * @param down the number of matching tiles below the selected tile
    * @param buttons  the grid of buttons
    */
   public void fallVertical(int row, int column, int up, int down, JButton[][] buttons) {
     
     // The number of blocks to be removed
     int height = up + down + 1;
     
     // Loop index starting at the bottom of the tiles to be removed
     int currentRow = row + down;
     
     // Moves the tiles down the column
     while (currentRow >= 0) {
       // Once there are no more tiles left to fall we create new ones
       try {
         buttons[currentRow][column].setBackground(buttons[currentRow - height][column].getBackground());
       }
       catch (ArrayIndexOutOfBoundsException e) {
         // An array of colors to assign to new jewels
         Color colors[] = {Color.green, Color.blue, Color.red, Color.yellow, Color.orange, Color.magenta, Color.pink, Color.white};
         buttons[currentRow][column].setBackground(colors[(int)(Math.random() * numJewels)]);
       }
       currentRow--;
     }
   }
   
   /** Switches the position of two jewels in the grid
    * @param firstSelectedRow  the row of the first selected tile
    * @param firstSelectedColumn  the column of the first selected tile
    * @param row  the row of the button that was switched
    * @param column  the column of the button that was switched
    * @param buttons  the grid of buttons
    */
   public void switchJewels(int firstSelectedRow, int firstSelectedColumn, int row, int column, JButton[][] buttons) {
     // Used to retain the original color so it can be assinged to the other tile after the first is replaced
     Color first = buttons[firstSelectedRow][firstSelectedColumn].getBackground();
     buttons[firstSelectedRow][firstSelectedColumn].setBackground(buttons[row][column].getBackground());
     buttons[row][column].setBackground(first);
   }
   
   /** Checks to see if the player has marked all the squares
    * @param buttons  the grid of buttons
    * @return boolean  tells if the user has won
    */
   public boolean checkWin(JButton buttons[][]) {
     // Nested loops to run across each button and see if it is marked
     for (int i = 0; i < buttons.length; i++) {
       for (int j = 0; j < buttons[i].length; j++) {
         if (!buttons[i][j].getText().equals("*"))
           return false;
       }
     }
     return true;
   }
   
   /** Main Method Calls Jewels Constructor based on input
     * 0 arguments -> Jewels()
     * 3 arguments -> Jewels(1st argument, 2nd argument, 3rd argument)
    */
   public static void main(String args[]) {
     if (args.length == 0)
       new Jewels();
     else if (args.length == 3) {
       try {
         new Jewels(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
       }
       catch(Exception e) {
         System.out.println("Please enter 3 integers to begin the game.");
       }
     }
     else {
       System.out.println("Please enter 3 valid integers to begin the game.");
     }
   }
}