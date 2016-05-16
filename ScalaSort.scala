package Try1;

class ScalaSort {
  def sort(gameArray: Array[FilesInformation]) {
    def swap(a: Int, b: Int) {
      val temp = gameArray(a);
      gameArray(a) = gameArray(b)
      gameArray(b) = temp
    }

    def quickSort(begin: Int, end: Int) {
      val temp = gameArray((begin + end) / 2).getCountOfLetters
      var i = begin
      var j = end
      while (i <= j) {
        while (gameArray(i).getCountOfLetters > temp) {
          i += 1
        }
        while (gameArray(j).getCountOfLetters < temp) {
          j -= 1
        }
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (begin < j) quickSort(begin, j)
      if (j < end) quickSort(i, end)
    }
    quickSort(0, gameArray.length - 1)
  }
}

