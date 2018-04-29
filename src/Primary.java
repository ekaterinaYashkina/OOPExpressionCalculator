/**
 * Created by Ekaterina Yashkina on 09-10-2017.
 */
public class Primary extends Expression {
    private Integer value;
    private Expression exp;

    public Expression getExp() {
        return exp;
    }

    public Primary(Expression exp){
        this.exp = exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

    public Primary(int value){
        this.value = value;
    }

    public String stringRepr(int i){
        String ans = "";
        for(int j=0; j<i; j++){
            ans+=tab;
        }
        String answer = ans;
        if (value!=null){
            answer +=("Primary: "+ value+"\n");
        }
        else if (exp!=null) {
            //System.out.println(1+" "+exp);
            answer+=exp.stringRepr(i+1);}
        return answer;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public String calculate(){
        if(value!=null){
            return value+"";
        }
        else {

            return exp.calculate();
        }
    }

    public String toJson(int depth)
    {

        String result = "";

        result= generateT(result, depth);
        result += "{\n";

        result=generateT(result, depth + 1);
        if (exp==null)
        result += "\"value\" : \"" + value + "\"\n";
        else result += "\"exp\" : " + out(depth+1) + "\n";

        result=generateT(result, depth);
        result += "}";

        return result;
    }

    public String out(int depth){
        if (exp!=null){

            return "\n"+exp.toJson(depth+1);
        }
        else return value.toString();
    }
}
