/**
 * Created by Ekaterina Yashkina on 09-10-2017.
 */
public class Factor extends Expression {
    /**
     * Enum operators available for Factor
     */
    enum Opcode{
        MULT, DIVIDE, NONE;
    }

    public Factor(){

    }

    public  Factor(Opcode op, Factor left1, Primary right){
        this.op = op;
        this.left1 = left1;
        this.right = right;
    }

    /**
     * Printing the "tree" of operations we got while parsing
     * @param i
     * @return
     */
    public String stringRepr(int i){
        String ans = "";
        for(int j=0; j<i; j++){
            ans+=tab;
        }
        String answer = ans;
        answer += ("Factor: \n");
        if(left!=null){
            answer+=(left.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Primary left: null");
        answer+=(ans+tab+"Opcode: "+op.toString()+"\n");
        if (right!=null){
            answer+=(right.stringRepr(i+1)+"\n");
        }
        else answer+=(ans+tab+"Primary right: null");
        return answer;
    }

    public Factor(Opcode op, Primary left, Primary right){
        this.op = op;
        this.left= left;
        this.right = right;
    }


    private  Opcode op;

    private Primary left, right;
    private  Factor left1;

    public Opcode getOp() {
        return op;
    }

    public void setOp(Opcode op) {
        this.op = op;
    }

    public Primary getLeft() {
        return left;
    }

    public void setLeft(Primary left) {
        this.left = left;
    }

    public Primary getRight() {
        return right;
    }

    public void setRight(Primary right) {
        right = right;
    }

    /**
     * Future method for calculating expression
     * @return
     */
    public String calculate(){
        switch (op){
            case MULT:
                if(left!=null)
                return (Integer.parseInt(left.calculate())*Integer.parseInt(right.calculate()))+"";
                else return (Integer.parseInt(left1.calculate())*Integer.parseInt(right.calculate()))+"";
            case DIVIDE:
                if(left!=null)
                return (Integer.parseInt(left.calculate())/Integer.parseInt(right.calculate()))+"";
                else return (Integer.parseInt(left1.calculate())/Integer.parseInt(right.calculate()))+"";
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
}
