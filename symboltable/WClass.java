package symboltable;

import utils.PrintError;

import java.util.Hashtable;

/**
 * Created by davidhao on 9/16/16.
 * this class is to specify each class with unique name
 */

public class WClass extends WType{

    protected String className;//each class name

    protected Hashtable<String,WClass> classes;

    protected String parentName;//parent name,if not noted with "null"

    protected Hashtable<String,WBasicType> fields;//fields

    protected Hashtable<String,WMethod> methods;//class's method

    protected VarTable fieldTable;

    protected MethodTable methodTable;
    public WClass(int lineNumber){
        super(lineNumber);
        fields = new Hashtable<String, WBasicType>();
        methods = new Hashtable<String, WMethod>();
        parentName = new String ("null");
        className = new String("null");
        classes = new Hashtable<String,WClass>();
        methodTable = new MethodTable();
        fieldTable = new VarTable();
    }

    public boolean insertMethod(String methodName,WMethod method){
        if (methods.containsKey(methodName)){
            return false;
        }else {
            method.setName(className);
            methods.put(methodName,method);
        }
        return true;
    }
    public boolean transField(WMethod method){
        for (String fieldName:fields.keySet()){
            method.insertVar(fieldName,fields.get(fieldName));
        }
        return true;
    }
    public WMethod getMethod(String methodName){
       if (methods.containsKey(methodName)) return methods.get(methodName);
        return (classes.get(parentName).getMethod(methodName));
    }
    public boolean insertField(String fieldName,WBasicType fieldType){
        if (fields.containsKey(fieldName)){
            return false;
        }else {
            fields.put(fieldName,fieldType);
        }
        return true;
    }
    public WBasicType getField (String fieldName){
        if (fields.containsKey(fieldName)) return fields.get(fieldName);
        return (classes.get(parentName).getField(fieldName));

    }
    public boolean hasField (String fieldName){
        if (fields.containsKey(fieldName)) return true;
        if (parentName.equals("null")) return false;
        return (classes.get(fieldName).hasField(fieldName));
    }
    public String getParent(){
        return parentName;
    }
    public boolean setParentName(){
        this.parentName = parentName;
        return true;
    }
    public boolean checkType (Hashtable<String,WClass> classes){
        for (String fieldName:fields.keySet()){
            if (!classes.containsKey(fields.get(fieldName).getType()) && !fields.get(fieldName).isBasic()){
                String errorString = "type not found : " + fields.get(fieldName).getType();
                PrintError.print(fields.get(fieldName).getLine(),errorString);
            }
        }
        for (String methodName : methods.keySet()){
            if (methodName.equals("main")) continue;
            methods.get(methodName).checkType(classes);
        }
        return true;
    }
    public boolean hasMethod(String methodName){
        if (methods.containsKey(methodName)) return true;
        if (parentName.equals("null")) return false;

        return (classes.get(parentName).hasMethod(methodName));
    }
    public boolean checkOverride(WClass obj){
        for (String methodName : methods.keySet()){
            if (!obj.hasMethod(methodName)) continue;
            methods.get(methodName).checkOverride(obj.getMethod(methodName));
        }
        return true;
    }
    public boolean hasClass(String className){
        return classes.containsKey(className);
    }
    public boolean addClass(String className,WClass obj){
        classes.put(className,obj);
        return true;
    }
    public WClass getWClass(String className){
        return classes.get(className);
    }
    public boolean transClasses(){
        for (String methodName : methods.keySet()){
            for (String className : classes.keySet()){
                methods.get(methodName).addClass(className,classes.get(className));
            }
        }
        return true;
    }
    public String getName(){
        return className;
    }
    public boolean setName(String className){
        this.className = className;
        return true;
    }
    public MethodTable getMethodTable(){
        return methodTable;
    }
    public VarTable getFieldTable(){
        return fieldTable;
    }

    public void fillTables(){
        if (parentName.equals("null")){
            fieldTable = new VarTable();
            methodTable = new MethodTable();
        }else {
            WClass parentClass = classes.get(parentName);
            parentClass.fillTables();
            methodTable = new MethodTable(parentClass.getMethodTable());
            fieldTable = new VarTable(parentClass.getFieldTable());
        }
        for (String methodName: methods.keySet()
             ) {
            methodTable.addMethod(methodName,className+"_"+methodName);
        }
        for (String fieldName : fields.keySet()){
            fieldTable.addVar(fieldName);
        }
    }

}
