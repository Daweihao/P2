package symboltable;

/**
 * Created by davidhao on 9/17/16.
 */
public class WBasicType extends WType {
    protected String    typeName;
    public WBasicType(String typeName){
        this.typeName = typeName;
    }
    boolean sameType(WBasicType type){
        return this.typeName.equals(type.typeName);
    }
    public String getType(){
        return typeName;
    }
    public boolean isBasic(){
        if(typeName == "int") return true;
        if (typeName == "array") return true;
        if (typeName == "boolean")return true;
        return false;
    }
}
