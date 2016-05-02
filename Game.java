import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * runs the game
 */

public class Game {
  public static void main(String[] args) {
    Wind win = new Wind();
    win.go();
  }
}
