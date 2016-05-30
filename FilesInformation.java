package Try1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Victoria on 15.05.2016.
 */
public class FilesInformation {
  private String nameOfFile;
  private int countOfLetters;
  private boolean winFlag;

  public void addName(String name) {
    nameOfFile = name;
  }

  public int getCountOfLetters() {
    return countOfLetters;
  }
  
  public boolean getWinFlag() {
    return winFlag;
  }

  public void printName() {
    System.out.println(nameOfFile + " -- " + countOfLetters);
  }

  public void setCountOfLetters() {

    try (BufferedReader br = new BufferedReader(
        new FileReader("E:\\workspace\\Hangman\\src\\Try1\\Saved\\" + nameOfFile))) {
      String s;
      countOfLetters = -2;
      while (true) {
        s = br.readLine();
        if(s == null) {
          break;
        }
        if (s.equals("0")) {
          winFlag = false;
        } else {
          winFlag = true;
        }
        countOfLetters++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}
