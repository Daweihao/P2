package utils;

import java.util.Vector;

/**
 * Created by davidhao on 9/18/16.
 * store error message and print together
 */

public class PrintError {
    private static Vector<String> errors = new Vector<>();

    public static void print(int lineNumber,String errorString){
        String msg = "Line" + lineNumber + ":" + errorString;
        errors.addElement(msg);
    }

    public static boolean hasError(){
        return !errors.isEmpty();
    }

    public static void printAll(){
        int sz = errors.size();
        for (int i = 0;i < sz; i++){
            System.out.println(errors.elementAt(i));
        }
    }
}
