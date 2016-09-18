package symboltable;

/**
 * Created by davidhao on 9/16/16.
 */
public class WType {
      protected int lineNumber = 0;//line

      public WType(){}
      public WType(int lineNumber){
            this.lineNumber = lineNumber;
      }

      public int getLine(){
        return lineNumber;
      }

      public void setLine(){
        this.lineNumber = lineNumber;
      }
}
