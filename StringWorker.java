import java.io.*;

/**
 * Performs specific operations with strings.
 */
public class StringWorker {
  private String codeWord;
  private boolean[] openPositions;

  /**
   * Notes in openPositions all positions witch contains the symbol
   * 
   * @param symbol guessed symbol
   * @return true if the symbol don't find and false if it finds
   */
  public boolean findAllSymb(char symbol) {
    boolean mistake = true;
    for (int i = 0; i < codeWord.length(); i++) {
      if (symbol == codeWord.charAt(i)) {
        openPositions[i] = true;
        mistake = false;
      }
    }
    return mistake;
  }

  public String getCodeWord() {
    return codeWord;
  }

  public void setCodeWord(String word) {
    codeWord = word;
  }

  public boolean[] getOpenPositions() {
    return openPositions;
  }

  public int getWordsLength() {
    return codeWord.length();
  }

  /**
   * Check the user victory
   * 
   * @return true if user wins and false if user lose
   */
  public boolean checkWin() {
    for (int i = 0; i < codeWord.length(); i++) {
      if (openPositions[i] == false) {
        return false;
      }
    }
    return true;
  }

  public void inicialise() {
    codeWord = chooseWord();
    openPositions = new boolean[codeWord.length()];
    for (int i = 0; i < codeWord.length(); i++) {
      openPositions[i] = false;
    }
  }

  /**
   * Take random word frome file and guess it to user
   * 
   * @return guessed word
   */
  public String chooseWord() {
    int wordCount = 0;
    String line = null;
    try (BufferedReader br =
        new BufferedReader(new FileReader("E:\\workspace\\Hangman\\src\\Try1\\words.txt"))) {
      while (true) {
        if (br.readLine() != null) {
          wordCount++;
        } else {
          break;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Word file is not found.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    int choosedWord = (int) (Math.random() * wordCount);
    try (BufferedReader br =
        new BufferedReader(new FileReader("E:\\workspace\\Hangman\\src\\Try1\\words.txt"))) {
      for (int i = 0; (line = br.readLine()) != null; i++) {
        if (i == choosedWord) {
          codeWord = line;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Word file is not found.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return codeWord;
  }

  /**
   * Give string witch must be visible to user
   * 
   * @return the visible string in necessary format
   */
  public String getGuessedWord() {
    StringBuffer guessedWord = new StringBuffer(codeWord + codeWord);
    for (int i = 0; i < codeWord.length(); i++) {
      if (openPositions[i] == false) {
        guessedWord.setCharAt(2 * i, '_');
      } else {
        guessedWord.setCharAt(2 * i, codeWord.charAt(i));
      }
      guessedWord.setCharAt(2 * i + 1, ' ');
    }
    return guessedWord.toString();
  }

}
