package symboltable;

/**
 * Created by davidhao on 9/16/16.
 */

 import utils.PrintError;

import java.util.Hashtable;

 public class WClasses extends WType{
        protected Hashtable<String,WClass> classes;
     //to store all classes//
        protected Hashtable<String,String> relations;
     //to store the relations of inheritance

        public WClasses(){
            classes = new Hashtable<String, WClass>();
            relations = new Hashtable<String,String>();
        }

        public boolean insertClass(String className,WClass obj){
            if (classes.containsKey(className)){
                return false;
            }
            else{
                obj.setName(className);
                classes.put(className,obj);
            }
            return true;
        }

        public WClass getWClass(String className){
            return classes.get(className);
        }

        public boolean checkLoop(){
            for (String className:relations.keySet()){
                String temp = relations.get(className);
                while (!temp.equals(className) && !temp.equals("null")){
                    temp = relations.get(temp);
                }
                if (temp.equals(className)){
                    String errorString = "class inheritence loop :begin from class"+ className;

                }
            }
            return false;
        }
        public void checkType(){
            for (String className:classes.keySet()){
                classes.get(className).checkType(classes);
            }
        }
        public boolean checkOverride(){
            for (String className: relations.keySet()){
                if (!relations.get(className).equals("null")){
                    classes.get(className).checkType(classes);
                }
            }
            return false;
        }



        public void update(){
         for (String className:classes.keySet()) {
             if (!classes.containsKey(classes.get(className).getParent()) &&
                     !classes.get(className).getParent().equals("null")) {
                 String errorString = "type not found :" + classes.get(className).getParent();
                 PrintError.print(classes.get(className).getLine(), errorString);
                 continue;
             }
             relations.put(className, classes.get(className).getParent());
         }
        for(String className : classes.keySet())

            {
                classes.get(className).fillTables();
            }
         }
        public boolean transClasses(WClass obj){
            for (String className : classes.keySet()){
                obj.addClass(className,classes.get(className));
            }
            return true;
        }

 }
