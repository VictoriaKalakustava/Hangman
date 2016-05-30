package Try1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Victoria on 15.05.2016.
 */
public class Sort {
  ArrayList<FilesInformation> files;
  static Random rand = new Random();



  public void findAllFiles() {
    files = new ArrayList<FilesInformation>();
    File directory = new File("E:\\workspace\\Hangman\\src\\Try1\\Saved");
    File[] fList = directory.listFiles();
    for (int i = 0; i < fList.length; i++) {
      FilesInformation newFile = new FilesInformation();
      newFile.addName(fList[i].getName());
      newFile.setCountOfLetters();
      files.add(newFile);
    }
  }
  
  public void giveStatistic() {
    findAllFiles();
    FilesInformation[] fileMas = new FilesInformation[files.size()];
    files.toArray(fileMas);
    ScalaStatistic myStat = new ScalaStatistic();
    int countOfLose = myStat.countOfLose(fileMas);
//    int countOfLose = 0;
//    for(FilesInformation info: fileMas){
//      countOfLose = info.getWinFlag() == false ? countOfLose+1 : countOfLose;
//    }
    System.out.println("Count of fails: " + countOfLose);
    System.out.println("Count of wins: " + (fileMas.length - countOfLose));
  }
  
  public void explainRandomNotice() {
    findAllFiles();
    String choosedFile = String.valueOf((int)(Math.random()*files.size()));
    try (BufferedReader br = new BufferedReader(
        new FileReader("E:\\workspace\\Hangman\\src\\Try1\\Saved\\" + choosedFile))) {
      String s;
      ScalaExplainer explainer = new ScalaExplainer();
      while (true) {
        s = br.readLine();
        if(s == null){
          break;
        }
        System.out.println(explainer.explain(s));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void printAllNames() {
    for (FilesInformation file : files) {
      file.printName();
    }
  }

  public void sortIt() {
    FilesInformation[] fileMas = new FilesInformation[files.size()];
    files.toArray(fileMas);
    long time = System.currentTimeMillis();
    qSort(fileMas, 0, fileMas.length - 1);
    time = System.currentTimeMillis() - time;
    files.clear();
    files.addAll(Arrays.asList(fileMas));
    System.out.println(time);
  }

  public void sortWithScala() {
    FilesInformation[] fileMas = new FilesInformation[files.size()];
    files.toArray(fileMas);
    ScalaSort mySorter = new ScalaSort();
    long time = System.currentTimeMillis();      
    mySorter.sort(fileMas);
    time = System.currentTimeMillis() - time;
    files.clear();
    files.addAll(Arrays.asList(fileMas));
    System.out.println("Scala time:" + time);
  }


  public void qSort(FilesInformation[] array, int begin, int end) {
    int i = begin;
    int j = end;
    int x = array[begin + rand.nextInt(end - begin + 1)].getCountOfLetters();
    while (i <= j) {
      while (array[i].getCountOfLetters() > x) {
        i++;
      }
      while (array[j].getCountOfLetters() < x) {
        j--;
      }
      if (i <= j) {
        FilesInformation temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        i++;
        j--;
      }
    }
    if (begin < j) {
      qSort(array, begin, j);
    }
    if (i < end) {
      qSort(array, i, end);
    }
  }
}


