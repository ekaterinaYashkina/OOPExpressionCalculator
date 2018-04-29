/**
 * Created by Ekaterina Yashkina on 09-10-2017.
 */
public class Relation extends Expression {
    /**
     * Opcode operations available for Relation
     */
    enum Opcode{
        LESS, LESS_EQ, GREATER, GREATER_EQ, EQUAL, NOT_EQ, NONE;
    }


    public  Relation(){

    }

    public Relation(Opcode op, Term left, Term right){
        this.op = op;
        this.left = left;
        this.right = right;
    }


    public String stringRepr(int i){
        String ans = "";
        for(int j=0; j<i; j++){
            ans+=tab;
        }
        String answer = ans;
        answer += ("Relation: \n");
        if(left!=null){
            answer+=(left.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Term left: null");
        answer+=(ans+tab+"Opcode: "+op.toString()+"\n");
        if (right!=null){
            answer+=(right.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Term right: null");
        return answer;
    }

    private Opcode op;
    private Term left, right;

    public Opcode getOp() {
        return op;
    }

    public void setOp(Opcode op) {
        this.op = op;
    }

    public Term getLeft() {
        return left;
    }

    public void setLeft(Term left) {
        this.left = left;
    }

    public Term getRight() {
        return right;
    }

    public void setRight(Term right) {
        this.right = right;
    }

    /**
     * Future method for calculation
     * @return
     */
    public String calculate() {
        switch (op) {
            case GREATER:
                return convertBoolean(Integer.parseInt(left.calculate()) > Integer.parseInt(right.calculate()))+"";
            case GREATER_EQ:
                return convertBoolean(Integer.parseInt(left.calculate()) >= Integer.parseInt(right.calculate()))+"";
            case EQUAL:
                return convertBoolean(Integer.parseInt(left.calculate()) == Integer.parseInt(right.calculate()))+"";
            case LESS:
                return convertBoolean(Integer.parseInt(left.calculate()) < Integer.parseInt(right.calculate()))+"";
            case LESS_EQ:
                return convertBoolean(Integer.parseInt(left.calculate()) <= Integer.parseInt(right.calculate()))+"";
            case NOT_EQ:
                return convertBoolean(Integer.parseInt(left.calculate()) != Integer.parseInt(right.calculate()))+"";
            default:
                return left.calculate();
        }

    }

    public int convertBoolean(boolean a){
        if (a==false) return 0;
        else return 1;
    }

    public String toJson(int depth){
        String jsonString ="";
        jsonString= generateT(jsonString, depth);
        jsonString+="{\n";
        jsonString = generateT(jsonString, depth+1);
        jsonString += "\"op\" : \"" + out() + "\",\n";
        if (left != null)
        {
            jsonString = generateT(jsonString, depth + 1);
            jsonString += "\"left\" :\n" + left.toJson(depth + 1);

            if (right != null)
                jsonString += ",\n";
            else jsonString+="\n";
        }

        if (right != null)
        {
            jsonString=generateT(jsonString, depth + 1);
            jsonString += "\"right\" :\n" + right.toJson(depth + 1) + "\n";
        }

       jsonString= generateT(jsonString, depth);
        jsonString += "}";

        return jsonString;
    }

    public String out(){
        return op.toString();
    }
}
