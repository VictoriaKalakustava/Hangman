
package Try1

class ScalaStatistic {
  def countOfLose(gameArray: Array[FilesInformation]) : Int = {
    gameArray.filter((i:FilesInformation) => i.getWinFlag == false).length
  }
}