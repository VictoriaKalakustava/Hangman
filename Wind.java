package Try1;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * create frame for game and contains main logic
 */
public class Wind {
  private JFrame frame = null;
  private JMenuBar menuBar = null;
  private JMenu windowMenu = null;
  private JMenu viewMenu = null;
  private JButton[] alphabet = null;
  private JLabel theWord = null, imageLabel = null;
  private StringWorker code = null;
  private JPanel panelBut = null, panel = null;
  private int countOfTry;
  private String[] imageAdresses;
  private boolean mistake = false;
  private boolean endGame = false;
  private boolean botFlag = false;
  private JMenuItem replayItem;
  private JMenuItem botItem;

  public Wind() {
    code = new StringWorker();
    frame = new JFrame("Hangman");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    menuBar = new JMenuBar();

    windowMenu = new JMenu("Window");
    JMenuItem quitItem = new JMenuItem("Quit");
    botItem = new JMenuItem("Bot");
    replayItem = new JMenuItem("Open saved game");
    JMenuItem statisticItem = new JMenuItem("Show statistic");
    quitItem.addActionListener(new QuitButtonListener());
    botItem.addActionListener(new BotListener());
    replayItem.addActionListener(new ReplayListener());
    statisticItem.addActionListener(new StatisticListener());
    windowMenu.add(quitItem);
    windowMenu.add(botItem);
    windowMenu.add(replayItem);
    windowMenu.add(statisticItem);

    viewMenu = new JMenu("View");
    JMenu lookAndFeel = new JMenu("LookAndFeel");
    JMenuItem itemMetal = new JMenuItem("Metal");
    itemMetal.addActionListener(new LookAndFeelListener());
    JMenuItem itemSystem = new JMenuItem("System");
    itemSystem.addActionListener(new LookAndFeelListener());
    lookAndFeel.add(itemMetal);
    lookAndFeel.add(itemSystem);
    viewMenu.add(lookAndFeel);

    menuBar.add(windowMenu);
    menuBar.add(viewMenu);

    theWord = new JLabel();
    panel = new JPanel();
    imageLabel = new JLabel();
    imageAdresses = new String[7];
    countOfTry = 6;

    createButtonPanel();

    int imageCount = 7;
    String address = "E:\\workspace\\Hangman\\src\\Try1\\";
    for (int i = 0; i < imageCount; i++)
      imageAdresses[i] = address + Integer.toString(i) + ".jpg";
  }

  /**
   * Set image on the frame, choose word for guessing. Set elements of the frame. Display the frame.
   */
  public void go() {
    imageLabel.setIcon(new ImageIcon(imageAdresses[0]));
    code.inicialise();
    setLabelFont();
    activateButtonPanel();
    theWord.setText(code.getGuessedWord());

    panel.setPreferredSize(new Dimension(230, 400));
    panel.add(BorderLayout.NORTH, theWord);
    panel.add(BorderLayout.CENTER, panelBut);

    frame.add(BorderLayout.NORTH, menuBar);
    frame.add(BorderLayout.EAST, panel);
    frame.add(BorderLayout.WEST, imageLabel);
    frame.setSize(500, 450);
    frame.setVisible(true);
  }

  /**
   * Specifies the initial conditions to restart the game.
   */
  public void restart() {
    countOfTry = 6;
    frame.remove(theWord);
    frame.remove(imageLabel);
    frame.remove(panel);
    frame.repaint();
    code = new StringWorker();
    theWord = new JLabel();
    panel = new JPanel();
    imageLabel = new JLabel();
    replayItem.setEnabled(true);
  }

  /**
   * Create new button panel with alphabet.
   */
  public void createButtonPanel() {
    panelBut = new JPanel();
    panelBut.setLayout(new GridLayout(8, 3));
    alphabet = new JButton[26];
    char c;
    for (int i = 0; i < alphabet.length; i++) {
      c = 'A';
      c += i;
      alphabet[i] = new JButton("" + c);
      alphabet[i].addActionListener(new MyButtonListener());
      panelBut.add(alphabet[i]);
    }
  }

  /**
   * Activate all buttons.
   */
  public void activateButtonPanel() {
    for (int i = 0; i < 26; i++) {
      alphabet[i].setEnabled(true);
      panelBut.add(alphabet[i]);
    }
  }

  /**
   * Set font settings for displayed word.
   */
  public void setLabelFont() {
    Font font = new Font("Century Gothic", Font.BOLD, 25);
    theWord.setVerticalAlignment(JLabel.CENTER);
    theWord.setHorizontalAlignment(JLabel.CENTER);
    theWord.setFont(font);
    theWord.setForeground(Color.BLACK);
  }

  /**
   * Check end of game. Offer to restart the game.
   */
  public void checkEndGame() {

    int choice = -1;
    endGame = false;
    if (code.checkWin()) {
      choice = JOptionPane.showConfirmDialog(frame, "You're win! Play again?");
    } else {
      if (countOfTry < 1) {
        choice = JOptionPane.showConfirmDialog(frame, "You're lose! Play again?");
        endGame = true;
      }
    }
    if (choice == JOptionPane.OK_OPTION) {
      restart();
      go();
    }
    if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CANCEL_OPTION) {
      System.exit(0);
    }
  }

  /**
   * Find guessed symbol in the word, controls cont of try, set new text on the label and repaint
   * frame.
   *
   * @param guessSymbol is a symbol chosen by the user
   */
  private void findLetter(char guessSymbol) {
    mistake = code.findAllSymb(guessSymbol);
    if (mistake == true) {
      countOfTry--;
    }
    imageLabel.setIcon(new ImageIcon(imageAdresses[6 - countOfTry]));
    theWord.setText(code.getGuessedWord());
    frame.repaint();
    if (!botFlag) {
      checkEndGame();
    }
  }

  class MyButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      // replayItem.setEnabled(false);
      char guessSymbol = event.getActionCommand().charAt(0);
      ((JButton) event.getSource()).setEnabled(false);
      findLetter(guessSymbol);
    }
  }

  class QuitButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      frame.setVisible(false);
      System.exit(0);
    }
  }
  
  class StatisticListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      Sort mySort = new Sort();
      mySort.giveStatistic();
      mySort.explainRandomNotice();
    }
  }

  class BotListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      replayItem.setEnabled(false);
      Thread botThread;
      botThread = new Thread(new BotThread());
      botThread.start();

    }
  }

  class ReplayListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      botItem.setEnabled(false);
      Thread replayThread;
      replayThread = new Thread(new ReplayThread());
      replayThread.start();
    }
  }

  class LookAndFeelListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        switch (e.getActionCommand()) {
          case "System":
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
            break;

          case "Metal":
            MetalLookAndFeel feel = new MetalLookAndFeel();
            UIManager.setLookAndFeel(feel);
            SwingUtilities.updateComponentTreeUI(frame);
            break;
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  class BotThread implements Runnable {
    public void run() {
      restart();
      go();
      endGame = false;
      Bot bot = new Bot();
      botFlag = true;
      bot.setDictionary();
      bot.startGame();
      bot.saveGame();
      checkEndGame();
      botFlag = false;
      replayItem.setEnabled(true);
    }
  }

  class ReplayThread implements Runnable {
    public void run() {
      restart();
      go();
      Bot bot = new Bot();
      botFlag = true;
      bot.replayGame();
      checkEndGame();
      botFlag = false;
      botItem.setEnabled(true);
    }
  }
  
  public void generateSaves() {
    for(int i = 33323; i < 100000; i++) {
      restart();
      go();
      endGame = false;
      Bot bot = new Bot();
      botFlag = true;
      bot.setDictionary();
      bot.startGame();
      bot.saveWithoutAsking(i);
    
      botFlag = false;
      replayItem.setEnabled(true);
    }
  }

  /**
   * Discribe bot's behavior
   */
  private class Bot {
    int wordsLength = 0;
    private ArrayList<String> dictionary;
    private ArrayList<String> notation;

    /**
     * Create list of words with similar length. Tries to find guessed word.
     */

    public void setDictionary() {
      dictionary = new ArrayList<>();
      wordsLength = code.getWordsLength();
      try (BufferedReader reader =
          new BufferedReader(new FileReader("E:\\workspace\\Hangman\\src\\Try1\\words.txt"))) {
        while (true) {
          String temp = reader.readLine();
          if (temp == null) {
            break;
          }
          if (temp.length() == wordsLength) {
            dictionary.add(temp);
          }
        }
      } catch (FileNotFoundException e) {
        System.out.println("Word file is not found.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void startGame() {

      String checkableWord;
      notation = new ArrayList<>();
      notation.add(code.getCodeWord());
      char checkableChar;
      for (int i = 0; i < wordsLength && !endGame && (dictionary != null);) {
        if(dictionary.size() == 0){
          break;
        }
        checkableWord = dictionary.get(0);
        checkableChar = checkableWord.charAt(i);
        notation.add(Character.toString(checkableChar));
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        alphabet[(int) checkableChar - 65].doClick();
        if (mistake == false) {
          removeNotContain(checkableChar);
          removeNotContainInPosition(i, checkableChar);
          i++;
        } else {
          removeAllContains(checkableChar);
        }
        if (countOfTry < 1 || code.checkWin()) {
          break;
        }
      }
      if (code.checkWin()) {
        notation.add(Integer.toString(1));
      } else {
        notation.add(Integer.toString(0));
      }
      System.out.println(notation);
    }

    public void replayGame() {
      try {
        String homeDirectory = "E:\\workspace\\Hangman\\src\\Try1\\Saved";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(homeDirectory));
        fileChooser.showOpenDialog(frame);
        File file = fileChooser.getSelectedFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        code.setCodeWord(bufferedReader.readLine());
        String line;
        while (true) {
          line = bufferedReader.readLine();
          if (line != "0" && line != "1") {
            char symbol = line.charAt(0);
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            alphabet[(int) symbol - 65].doClick();
          }
          if (countOfTry < 1 || code.checkWin()) {
            bufferedReader.close();
            break;
          }
        }
        code.checkWin();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void saveGame() {
      try {
        String homeDirectory = "E:\\workspace\\Hangman\\src\\Try1\\Saved";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(homeDirectory));
        fileChooser.showSaveDialog(frame);
        File file = fileChooser.getSelectedFile();
        if (file != null) {
          FileWriter fileWriter = new FileWriter(file);

          for (String temp : notation) {
            fileWriter.write(temp + "\n");
          }
          fileWriter.write("");
          fileWriter.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    
    public void saveWithoutAsking(int i)  {
      System.out.println("saveWithoyw");
        try  {
          String homeDirectory = "E:\\workspace\\Hangman\\src\\Try1\\Saved\\";
          File file = new File(homeDirectory + i);
          
          if (file != null) {
            FileWriter fileWriter = new FileWriter(file);
  
            for (String temp : notation) {
              fileWriter.write(temp + "\n");
            }
            fileWriter.write("");
            fileWriter.close();
          }
        } catch (IOException ex)  {
          ex.printStackTrace();
        }
        
    }

    
    /**
     * Remove all word in the list witch not contain the symbol.
     *
     * @param symbol the guessed letter
     */
    public void removeNotContain(char symbol) {
      int i = 0;
      while (i < dictionary.size()) {
        if (!(dictionary.get(i).contains(String.valueOf(symbol)))) {
          dictionary.remove(i);
        } else {
          i++;
        }
      }
    }

    /**
     * Remove all word in the list witch not contain the symbol on the position.
     *
     * @param position witch must contain the symbol
     * @param symbol the guessed letter
     */
    public void removeNotContainInPosition(int position, char symbol) {
      int i = 0;
      while (i < dictionary.size()) {
        if ((dictionary.get(i).charAt(position) != symbol)) {
          dictionary.remove(i);
        } else {
          i++;
        }
      }
    }

    /**
     * Remove all word in the list witch contain the symbol.
     *
     * @param symbol the guessed letter
     */
    public void removeAllContains(char symbol) {
      int i = 0;
      while (i < dictionary.size()) {
        if ((dictionary.get(i).contains(String.valueOf(symbol)))) {
          dictionary.remove(i);
        } else {
          i++;
        }
      }
    }
  }
}
