import symboltable.WClasses;
import syntaxtree.Node;
import utils.PrintError;
import visitor.ChInitVisitor;
import visitor.ChTypeErrorVisitor;
import visitor.CheckUdfVisitor;
import visitor.SyTableBuilderVisitor;

public class P2 {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         System.out.println("Program parsed successfully");
         WClasses w_classes = new WClasses();

       root.accept(new SyTableBuilderVisitor(),w_classes); // Your assignment part is invoked here.
         if (PrintError.hasError()){
            PrintError.printAll();
            return;
         }
         w_classes.update();
         w_classes.checkLoop();
         w_classes.checkOverride();
         w_classes.checkType();
         if (PrintError.hasError()){
            PrintError.printAll();
            return;
         }

         root.accept(new CheckUdfVisitor(),w_classes);
         if (PrintError.hasError()){
            PrintError.printAll();
            return;
         }

         root.accept(new ChTypeErrorVisitor(),w_classes);
         root.accept(new ChInitVisitor(),w_classes);
         if (PrintError.hasError()){
            PrintError.printAll();
         }

      }
      catch (TokenMgrError e){
          e.printStackTrace();
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
      catch (Exception e){
          e.printStackTrace();
      }
   }
} 



