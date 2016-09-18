package symboltable;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by davidhao on 9/17/16.
 * this class is to store variable in a hashtable
 */
public class VarTable {
    protected Vector<String> list;
    protected Hashtable<String, Integer> offsets;

    public VarTable(){
        list = new Vector<>();
        offsets = new Hashtable<>();
    }
    public VarTable(VarTable fieldTable) {
        list = new Vector<>(fieldTable.getList());
        offsets = new Hashtable<String,Integer>(fieldTable.getOffsets());
    }
    public Vector<String> getList(){
        return list;
    }
    public Hashtable<String,Integer> getOffsets(){
        return offsets;
    }
    public int getOffset(String name){
        return offsets.get(name);
    }
    public int getVarNumber(){
        return list.size();
    }

    //to add and update the variable list
    public void addVar(String name){
        list.addElement(name);
        if(offsets.containsKey(name)){
            offsets.remove(name);
        }
        offsets.put(name,list.size()-1);
    }
}