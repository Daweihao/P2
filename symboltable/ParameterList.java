package symboltable;

import java.util.Vector;

/**
 * Created by davidhao on 9/17/16.
 * set up a parameterlist to know how many name we have got
 */
public class ParameterList extends WType{
    Vector<WBasicType> paraTypes;
    public ParameterList(int lineNumber) {
        paraTypes = new Vector<WBasicType>();
    }
    public void insertPara(WBasicType paraType){
        paraTypes.addElement(paraType);
    }
    public void addhead(WBasicType headType){
        paraTypes.addElement(headType);
    }
    public WBasicType getTypeAt(int index){
        return paraTypes.elementAt(index);
    }
    Vector<WBasicType> getTypes(){
        return paraTypes;
    }
    public int getSize(){
        return paraTypes.size();
    }

    /* to check whether paralist is the same */
    public boolean isSameAs(ParameterList paras){
        if (paraTypes.size() != paras.getTypes().size())return false;
        for (int index = 0;index != paraTypes.size();++index){
            if (!paraTypes.get(index).sameType(paras.getTypes().get(index))) return false;
        }
        return true;
    }
    public int getNum(){
        return paraTypes.size();
    }

}
