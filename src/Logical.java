/**
 * Created by Ekaterina Yashkina on 09-10-2017.
 */
public class Logical extends Expression {
    /**
     * Enumerator for operations available for Logical
     */
    enum Opcode{
        AND, OR, XOR, NONE;
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

        if( left1!=null){
            jsonString = generateT(jsonString, depth + 1);
            jsonString += "\"left1\" :\n" + left1.toJson(depth + 1);

            if (right != null)
                jsonString += ",\n";
            else jsonString+="\n";
        }

        if (right != null)
        {
            jsonString=generateT(jsonString, depth + 1);
            jsonString += "\"right\" :\n" + right.toJson(depth + 1) + "\n";
        }

        jsonString=generateT(jsonString, depth);
        jsonString += "}";

        return jsonString;
    }

    public String out(){
        return op.toString();
    }


    public Logical(){

    }

    /**
     *
     * @param op - Opcode for relation
     * @param left - left part of logical
     * @param right - right part of logical
     */
    public Logical(Opcode op, Relation left, Relation right){
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public Logical getLeft1() {
        return left1;
    }

    public void setLeft1(Logical left1) {
        this.left1 = left1;
    }

    public Logical(Opcode op, Logical left1, Relation right ){
        this.left1 = left1;
        this.op = op;
        this.right = right;

    }
    private Opcode op;

    public Opcode getOp() {
        return op;
    }

    public void setOp(Opcode op) {
        this.op = op;
    }

    private Relation left, right;

    private Logical left1;

    public Relation getLeft() {
        return left;
    }

    public void setLeft(Relation left) {
        this.left = left;
    }

    public Relation getRight() {
        return right;
    }

    public void setRight(Relation right) {
        this.right = right;
    }

    /**
     * Prints "tree" of operations built during the parsing
     * @param i
     * @return
     */
    public String stringRepr(int i){
        String ans = "";
        for(int j=0; j<i; j++){
            ans+=tab;
        }
        String answer = ans;
        answer += ("Logical: \n");
        if(left!=null){//call the same function for left part to get the primary
            answer+=(left.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Relation left: null");
        answer+=(ans+tab+"Opcode: "+op.toString()+"\n");
        if (right!=null){//call the same function for right part to get the primary
            answer+=(right.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Relation right: null");
        return answer;
    }

    /**
     * Scheme method for calculation
     * @ return - result of calculation
     */
    public String calculate(){
        switch (op){
            case OR:
                if(left!=null)
                return (Integer.parseInt(left.calculate()) | Integer.parseInt(right.calculate()))+"";
                else return (Integer.parseInt(left1.calculate()) | Integer.parseInt(right.calculate()))+"";
            case AND:
                if(left!=null)
                return (Integer.parseInt(left.calculate()) & Integer.parseInt(right.calculate()))+"";
                else return (Integer.parseInt(left1.calculate()) & Integer.parseInt(right.calculate()))+"";
            case XOR:
                if(left!=null)
                return (Integer.parseInt(left.calculate()) ^ Integer.parseInt(right.calculate()))+"";
                else return (Integer.parseInt(left1.calculate()) ^ Integer.parseInt(right.calculate()))+"";
            default:
                if(left!=null)
                return left.calculate();
                else return left1.calculate();
        }

    }
}
