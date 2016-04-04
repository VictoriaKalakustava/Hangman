package Try1;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

  public Wind() {
    code = new StringWorker();
    frame = new JFrame("Hangman");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    menuBar = new JMenuBar();
    
    windowMenu = new JMenu("Window");
    JMenuItem quitItem = new JMenuItem("Quit");
    quitItem.addActionListener(new QuitButtonListener());
    windowMenu.add(quitItem);
    
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
  }

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

  public void activateButtonPanel() {
    for (int i = 0; i < 26; i++) {
      alphabet[i].setEnabled(true);
      panelBut.add(alphabet[i]);
    }
  }
  
  public void setLabelFont() {
    Font font = new Font("Century Gothic", Font.BOLD, 25);
    theWord.setVerticalAlignment(JLabel.CENTER);
    theWord.setHorizontalAlignment(JLabel.CENTER);
    theWord.setFont(font);
    theWord.setForeground(Color.BLACK);
  }

  public void checkEndGame() {

    int choice = -1;

    if (code.checkWin()) {
      choice = JOptionPane.showConfirmDialog(frame, "You're win! Play again?");
    } else {
      if (countOfTry < 1) {
        choice = JOptionPane.showConfirmDialog(frame, "You're lose! Play again?");
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

  class MyButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      char guessSymbol = event.getActionCommand().charAt(0);
      boolean mistake = code.findAllSymb(guessSymbol);
      if (mistake == true) {
        countOfTry--;
      }
      imageLabel.setIcon(new ImageIcon(imageAdresses[6 - countOfTry]));
      theWord.setText(code.getGuessedWord());
      ((JButton) event.getSource()).setEnabled(false);
      checkEndGame();
    }
  }

  class QuitButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      frame.setVisible(false);
      System.exit(0);
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
}
