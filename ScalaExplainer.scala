

package Try1

class ScalaExplainer {
  def explain(s:Any): Any ={
    s match {
      case str:String if(str.length > 1) => "Guessed word is \""+s+"\""
      case str: String if(str.equals("0"))=> "Bot lose"
      case str:String if(s.equals("1"))=> "Bot won"
      case _ => "Bot put forth \'"+s+"\'"
    }
  }
}