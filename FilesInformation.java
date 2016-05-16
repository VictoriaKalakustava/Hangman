package Try1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

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

  public void printName() {
    System.out.println(nameOfFile + " -- " + countOfLetters);
  }

  public void setCountOfLetters() {

    try (BufferedReader br = new BufferedReader(
        new FileReader("E:\\workspace\\Hangman\\src\\Try1\\Saved\\" + nameOfFile))) {
      String s;
      countOfLetters = -2;
      while ((s = br.readLine()) != null) {
        if (s == "0") {
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
