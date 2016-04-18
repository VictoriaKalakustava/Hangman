
import java.io.*;

public class StringWorker {
    private String codeWord;
    private boolean[] openPositions;

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

    public boolean[] getOpenPositions() {
        return openPositions;
    }

    public int getWordsLength() {
        return codeWord.length();
    }

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

    public String chooseWord() {
        int wordCount = 0;
        String line = null;
        try (BufferedReader br =
                     new BufferedReader(new FileReader("E:\\workspace\\Hangman\\src\\Try1\\words.txt"))) {
            while(true){
                if(br.readLine() != null){
                    wordCount++;
                }
                else {
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
