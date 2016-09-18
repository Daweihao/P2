package symboltable;

import utils.PrintError;

import java.util.Hashtable;
import java.util.Vector;


/**
 * Created by davidhao on 9/17/16.
 * this class is to store the method in each class
 */
public class WMethod extends WType {
    protected Hashtable<String,WBasicType> vars;//local variables
    protected ParameterList parameters;//parameterlist
    protected Hashtable<String,WClass> classes;//global classes info
    protected WBasicType returnType;
    protected String className;//class belongs to
    protected Vector<String> varInit;//variables initialized
    protected VarTable varTable;

    public WMethod(int lineNumber){
        super(lineNumber);
        this.parameters = new ParameterList(lineNumber);
        vars = new Hashtable<>();
        returnType = new WBasicType("void");
        className = new String("null");
        classes = new Hashtable<>();
        varInit = new Vector<>();
        varTable = new VarTable();
    }
    public boolean insertVar(String varName,WBasicType varType){
        if (vars.containsKey(varName)){
            return false;
        }else {
            vars.put(varName,varType);
            varTable.addVar(varName);
        }
        return true;
    }
    public WBasicType getVar(String varName) {
        if (vars.containsKey(varName)) return vars.get(varName);
        if (classes.get(className).hasField(varName)) return classes.get(className).getField(varName);
        return (classes.get(classes.get(className).getParent()).getField(varName));
    }
    public ParameterList getParas(){
        return parameters;
    }
    public boolean setParas(ParameterList paras){
        parameters = paras;
        return true;
    }
    public boolean addParas(WBasicType paraType){
        parameters.insertPara(paraType);
        return true;
    }
    public WBasicType getRet(){
        return returnType;
    }
    public boolean setRet(WBasicType retType){
        returnType = retType;
        return true;
    }

    public boolean checkType(Hashtable<String,WClass> classes){
        if (!classes.containsKey(returnType.getType()) && !returnType.isBasic()){
            String errorString = "type not found : " + returnType.getType();
            PrintError.print(lineNumber,errorString);
        }
        for (String varName:vars.keySet()){
            if (!classes.containsKey(vars.get(varName).getType()) && !vars.get(varName).isBasic()){
             String errorString = "type not found : " + vars.get(varName).getType();
                PrintError.print(lineNumber,errorString);

            }
        }
        return true;
    }
    public boolean checkOverride(WMethod method){
        if (!parameters.isSameAs(method.getParas())||
                !returnType.getType().equals(method.getRet().getType())){
            String errorString = "override method";
            PrintError.print(lineNumber,errorString);
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
    public boolean hasVar(String varName){
        if (vars.containsKey(varName)) return true;
        if (classes.get(className).hasField(varName)) return true;
        if (classes.get(className).parentName.equals("null")) return false;
        return (classes.get(classes.get(className).getParent())).hasField(varName);
    }
    public boolean hasLocal(String varName){
        return (vars.containsKey(varName));
    }
    public boolean subClass(WBasicType t1 , WBasicType t2){
        String className = t1.getType();
        while (!className.equals(t2.getType())&& !className.equals("null")){
            className = classes.get(className).getParent();
        }
        if (className.equals(t2.getType())) return true;
        return false;
    }
    public boolean typeMatch(WBasicType t1,WBasicType t2){
        if (t1.sameType(t2)) return true;
        if (t1.getType().equals("void")|| t2.getType().equals("void")) return true;
        if (!(t1 instanceof WClassType)||!(t2 instanceof WClassType)) return  false;
        if (!(subClass(t1,t2))) return false;
        return true;
    }
    public String getName(){
        return className;
    }
    public boolean setName(String className){
        this.className = className;
        return true;
    }
    public boolean isInit(String varName){
        if (!vars.containsKey(varName)) return true;
        return varInit.contains(varName);
    }
    public void insertInit(String varName){
        varInit.addElement(varName);
    }
    public int getParaNum(){
        return parameters.getNum();
    }
    public VarTable getVarTable(){
        return varTable;
    }
    public boolean matchType(ParameterList paras){
        if (parameters.getSize() !=paras.getTypes().size()) return false;
        for (int index =0;index!=parameters.getSize();++index){
            if(!typeMatch(parameters.getTypeAt(index),paras.getTypes().get(index)) &&
                    !typeMatch(paras.getTypes().get(index),parameters.getTypeAt(index))){
                return false;
            }
        }
        return true;
    }

}
