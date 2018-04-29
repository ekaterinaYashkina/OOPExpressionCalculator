/**
 * Created by Ekaterina Yashkina on 09-10-2017.
 */
public class Term extends Expression {

    /**
     * Enumerator representing the operation available to Term
     */
    enum Opcode{
        PLUS, MINUS, NONE;
    }

    public  Term(){

    }

    public Term(Opcode op, Factor left, Factor right){
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public Term getLeft1() {
        return left1;
    }

    public void setLeft1(Term left1) {
        this.left1 = left1;
    }

    public Term(Opcode op, Term left1, Factor right){
        this.op = op;
        this.left1 = left1;
        this.right = right;

    }
    /**
     * "Tree" representation method for term
     * @param i
     * @return
     */
    public String stringRepr(int i){
        String ans = "";
        for(int j=0; j<i; j++){
            ans+=tab;
        }
        String answer = ans;
        answer += ("Term: \n");
        if(left!=null){
            answer+=(left.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Factor left: null");
        answer+=(ans+tab+"Opcode: "+op.toString()+"\n");
        if (right!=null){
            answer+=(right.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Factor right: null");
        return answer;
    }

    private Opcode op;
    private Factor left, right;
    private Term left1;

    public Opcode getOp() {
        return op;
    }

    public void setOp(Opcode op) {
        this.op = op;
    }

    public Factor getLeft() {
        return left;
    }

    public void setLeft(Factor left) {
        this.left = left;
    }

    public Factor getRight() {
        return right;
    }

    public void setRight(Factor right) {
        this.right = right;
    }

    //Scheme method for calculating the result of expression
    public String calculate(){
        switch (op){
            case PLUS:
                if(left!=null)
                return (Integer.parseInt(left.calculate())+Integer.parseInt(right.calculate()))+"";
                else return (Integer.parseInt(left1.calculate())+Integer.parseInt(right.calculate()))+"";
            case MINUS:
                if(left!=null)
                return (Integer.parseInt(left.calculate())-Integer.parseInt(right.calculate()))+"";
                else return (Integer.parseInt(left1.calculate())-Integer.parseInt(right.calculate()))+"";
            default:
                if(left!=null)
                return left.calculate();
                else return left1.calculate();
        }
    }

    public String out(){
        return op.toString();
    }

    public String toJson(int depth){
        String jsonString ="";
        jsonString= generateT(jsonString, depth);
        jsonString+="{\n";
        jsonString = generateT(jsonString, depth+1);
        jsonString += "\"op\" : \"" + out() + "\",\n";
        if (left != null)
        {
            jsonString= generateT(jsonString, depth + 1);
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

       jsonString= generateT(jsonString, depth);
        jsonString += "}";

        return jsonString;
    }
}
