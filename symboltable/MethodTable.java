package symboltable;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by davidhao on 9/17/16.
 */
public class MethodTable {
    protected Hashtable<String,String> labels;
    protected Vector<String> list;

    public MethodTable(){
        labels = new Hashtable<>();
        list = new Vector<>();
    }
    public MethodTable(MethodTable methodTable){
        labels = new Hashtable<>(methodTable.getLabels());
        list = new Vector<>(methodTable.getList());
    }
    public Hashtable<String,String> getLabels(){
        return labels;
    }
    public Vector<String> getList(){
        return list;
    }
    public String getLabel(String name){
        return labels.get(name);
    }
    public String getLabel(int index){
        return labels.get(list.elementAt(index));
    }
    public int getOffset(String name){
        for (int i = 0;i<list.size();i++) {
            if (list.elementAt(i).equals(name))
            return i;
        }
        return -1;
    }
    public void addMethod(String name,String labelName){
        if (labels.containsKey(labelName)){
            labels.remove(name);
            labels.put(name, labelName);
        }
        else {
            labels.put(name, labelName);
            list.addElement(name);
        }
    }
    public int getMethodNumber(){
        return list.size();
    }

}
