import org.junit.*;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;

/** Test the Jewels Class
See descriptions of tests in attached testing report */


public class JewelsTester {
  
  /** Tests the spotsTouch Method */
  @Test
  public void testSpotsTouch() {
    Jewels game = new Jewels();
    
    assertTrue(game.spotsTouch(0, 0, 0, 1));
    assertTrue(game.spotsTouch(0, 0, 1, 0));
    assertTrue(game.spotsTouch(4, 5, 4, 4));
    assertTrue(game.spotsTouch(4, 5, 3, 5));
    
    assertFalse(game.spotsTouch(0, 0, 4, 0));
    assertFalse(game.spotsTouch(5, 5, 1, 3));
    assertFalse(game.spotsTouch(2, 9, 3, 4));
  }
  
  /** Tests the upMatch Method */
  @Test
  public void testUpMatch() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
        a[i][j].setBackground(Color.red);
      }
    }
    
    assertEquals(0, game.upMatch(0, 2, a));
    assertEquals(1, game.upMatch(1, 2, a));
    assertEquals(2, game.upMatch(2, 2, a));
    
  }
  
  /** Tests the downMatch Method */
  @Test
  public void testDownMatch() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
        a[i][j].setBackground(Color.red);
      }
    }
    
    assertEquals(2, game.downMatch(3, 0, 2, a));
    assertEquals(1, game.downMatch(3, 1, 2, a));
    assertEquals(0, game.downMatch(3, 2, 2, a));
  }
  
  /** Tests the leftMatch Method */
  @Test
  public void testLeftMatch() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
        a[i][j].setBackground(Color.red);
      }
    }
    
    assertEquals(0, game.leftMatch(2, 0, a));
    assertEquals(1, game.leftMatch(2, 1, a));
    assertEquals(2, game.leftMatch(2, 2, a));
  }
  
  /** Tests the rightMatch Method */
  @Test
  public void testRightMatch() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
        a[i][j].setBackground(Color.red);
      }
    }
    
    assertEquals(2, game.rightMatch(3, 2, 0, a));
    assertEquals(1, game.rightMatch(3, 2, 1, a));
    assertEquals(0, game.rightMatch(3, 2, 2, a));
  }
  
  /** Tests the markHorizontal Method */
  @Test
  public void testMarkHorizontal() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
      }
    }
    
    JButton[][] b = new JButton[3][3];
    
    for (int i = 0; i < b.length; i++) {
      for (int j = 0; j < b[i].length; j++) {
        b[i][j] = new JButton();
      }
    }
    
    game.markHorizontal(2, 2, 2, 0, a);
    
    for (int i = 0; i < 3; i++)
      b[2][i].setText("*");
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(b[i][j].getText(), a[i][j].getText());
      }
    }
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setText("");
      }
    }
    
     game.markHorizontal(2, 1, 1, 1, a);
     
     for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(b[i][j].getText(), a[i][j].getText());
      }
    }
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setText("");
      }
    }
    
     game.markHorizontal(2, 0, 0, 2, a);
     
     for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(b[i][j].getText(), a[i][j].getText());
      }
    }
    
  }
  
  /** Tests the markVertical Method */
  @Test
  public void testMarkVertical() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
      }
    }
    
    JButton[][] b = new JButton[3][3];
    
    for (int i = 0; i < b.length; i++) {
      for (int j = 0; j < b[i].length; j++) {
        b[i][j] = new JButton();
      }
    }
    
    game.markVertical(0, 0, 0, 2, a);
    
    for (int i = 0; i < 3; i++)
      b[i][0].setText("*");
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(b[i][j].getText(), a[i][j].getText());
      }
    }
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setText("");
      }
    }
    
     game.markVertical(1, 0, 1, 1, a);
     
     for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(b[i][j].getText(), a[i][j].getText());
      }
    }
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setText("");
      }
    }
    
     game.markVertical(2, 0, 2, 0, a);
     
     for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(b[i][j].getText(), a[i][j].getText());
      }
    }
  }
  
  /** Tests the fallHorizontal Method */
  @Test
  public void testFallHorizontal() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    JButton[][] b = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
        a[i][j].setBackground(Color.white);
        b[i][j] = new JButton();
        b[i][j].setBackground(Color.white);
      }
    }
    
    game.fallHorizontal(2, 0, 0, 2, a);
    
    for (int i = 0; i < 3; i++) {
      assertNotSame(a[0][i].getBackground(), b[0][i].getBackground());
    }
    
    for (int i = 1; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(a[i][j].getBackground(), b[i][j].getBackground());
      }
    }
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setBackground(Color.white);
      }
    }
    
    game.fallHorizontal(0, 0, 0, 2, a);
    
    for (int i = 0; i < 3; i++) {
      assertNotSame(a[0][i].getBackground(), b[0][i].getBackground());
    }
    
    for (int i = 1; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(a[i][j].getBackground(), b[i][j].getBackground());
      }
    }
  }
  
  /** Tests the fallVertical Method */
  @Test
  public void testFallVertical() {
    Jewels game = new Jewels();
    JButton[][] a = new JButton[3][3];
    JButton[][] b = new JButton[3][3];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
        a[i][j].setBackground(Color.white);
        b[i][j] = new JButton();
        b[i][j].setBackground(Color.white);
      }
    }
    
    game.fallVertical(2, 0, 2, 0, a);
    
    for (int i = 0; i < 3; i++) {
      assertNotSame(a[i][0].getBackground(), b[i][0].getBackground());
    }
    
    for (int i = 1; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(a[j][i].getBackground(), b[j][i].getBackground());
      }
    }
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setBackground(Color.white);
      }
    }
    
    game.fallVertical(0, 0, 0, 2, a);
    
    for (int i = 0; i < 3; i++) {
      assertNotSame(a[i][0].getBackground(), b[i][0].getBackground());
    }
    
    for (int i = 1; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        assertEquals(a[j][i].getBackground(), b[j][i].getBackground());
      }
    }
  }
  
  /** Tests the switchJewels Method */
  @Test
  public void testSwitchJewels() {
    Jewels game = new Jewels();
    JButton[][] a = {{new JButton(), new JButton()}, {new JButton(), new JButton()}};
    
    a[0][0].setBackground(Color.red);
    a[0][1].setBackground(Color.yellow);
    a[1][0].setBackground(Color.blue);
    a[1][1].setBackground(Color.magenta);
    
    game.switchJewels(0, 0, 0, 1, a);
    
    JButton[][] b = {{new JButton(), new JButton()}, 
                     {new JButton(), new JButton()}};
    b[0][0].setBackground(Color.yellow);
    b[0][1].setBackground(Color.red);
    b[1][0].setBackground(Color.blue);
    b[1][1].setBackground(Color.magenta);
    
    assertEquals(a[0][0].getBackground(), b[0][0].getBackground());
    assertEquals(a[0][1].getBackground(), b[0][1].getBackground());
    assertEquals(a[1][0].getBackground(), b[1][0].getBackground());
    assertEquals(a[1][1].getBackground(), b[1][1].getBackground());
    
    
  }
  
  /** Tests the checkWin Method */
  @Test
  public void testCheckWin() {
    
    Jewels game = new Jewels();
    JButton[][] a = new JButton[4][4];
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = new JButton();
      }
    }
    
    assertEquals(false, game.checkWin(a));
    
    a[0][0].setText("*");
    
    assertEquals(false, game.checkWin(a));
    
     a[0][0].setText("");
     a[3][3].setText("*");
     
     assertEquals(false, game.checkWin(a));
     
     for (int i = 2; i < 3; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setText("*");
      }
    }
     
    assertEquals(false, game.checkWin(a));
    
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j].setText("*");
      }
    }
    
    assertEquals(true, game.checkWin(a));
  } 
}