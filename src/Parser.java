import java.util.LinkedList;

/**
 * Created by Ekaterina Yashkina on 10-10-2017.
 */
public class Parser {
    private String input;//the line to parse
    private int index;//points to char to recognize
    public Parser(String input){
        this.input = input;
    }
    public boolean lettersRead(){
        return index>=input.length();
    }//have we read all letters

    /**
     * Main method - parsing expression accepts only division on two parts:
     * if we have three expression, they will not be parsed, thus, we should return list of expressions
     * @return
     */
    public Logical parse() throws Exception{
        Logical result = parseLogical();
        return result;
    }

    /**
     * Parse expression into few logical parts
     * @return - parsed logical expression
     */
    private Logical parseLogical() throws Exception{
        int counter = 0;
        Relation parsed = parseRelation();
        Logical result = null;
        if (!nextLogicalFound()){
            result = new Logical(Logical.Opcode.NONE, parsed, null);
        }
        else {
            while (nextLogicalFound()) {//if the next char is opcode for logical, we manipulate with it
                Logical.Opcode op = readNextLogical();
                Relation right = parseRelation();
                if (right != null) {
                    if (counter == 0)
                    { result = new Logical(op, (Relation) parsed, (Relation) right);
                    counter++;}
                    else {
                        result = new Logical(op, result, right);
                    }
                }
            }
        }
        //else we create logical expression if only left part available
        return result;

    }

    /**
     * Parsing logical to relation parts
     * @return
     */
    private Relation parseRelation()throws Exception{
        Term parsed = parseTerm();//parse the first term
        Relation result = null;
        if(nextRelationFound()){//if we found relation opcode - manipulate with it
            Relation.Opcode op = readNextRelation();
            Term right = parseTerm();
            if (right!=null){
                result = new Relation(op, (Term) parsed, (Term) right);
                return result;
            }

        }
        result = new Relation(Relation.Opcode.NONE, parsed, null);
        return result;
    }

    /**
     * Parsing term into factor parts
     * @return
     */
    private Term parseTerm() throws Exception{
        int counter = 0;
        Factor parsed = parseFactor();
        Term result = null;
        if (!nextTermFound()) result = new Term(Term.Opcode.NONE, parsed, null);
        while(nextTermFound()){
            Term.Opcode op = readNextTerm();
            Factor right = parseFactor();
            if (right!=null){
                if(counter==0)
                {result = new Term(op, (Factor) parsed, (Factor) right);
                counter++;}
                else result = new Term(op, result, right);
            }
        }
        return result;
    }

    /**
     * Parsing factor into primary parts
     * @return
     */
    private Factor parseFactor() throws Exception{
        int counter = 0;
        Primary parsed = parsePrimary();
        Factor result = null;
        if(!nextFactorFound()) result = new Factor(Factor.Opcode.NONE, parsed, null);
        while(nextFactorFound()){
            Factor.Opcode op = readNextFactor();
            Primary right = parsePrimary();
            if (right!=null){
                if (counter==0){
                result = new Factor(op,  parsed, right);
                counter++;}
                else  result = new Factor(op, result, right);
            }

        }
        return result;
    }

    private Primary parsePrimary() throws java.lang.Exception{
        Expression parsed = parseParenthesized();//first check if we have parenthesizes(it will restart parsing new
                                                //new expression in parenthesizes
        Primary result;
        if(nextPrimaryFound()){//if we found primary value - int, we read the whole value
            int primary = readNextPrimary();
            result = new Primary(primary);
            return result;
        }
        else if(parsed==null&&!nextPrimaryFound()) throw new Exception("Illegal operand");
        result = new Primary(parsed);
        return result;
    }


    /**
     * Checking for parenthesizes
     * @return
     */
    public Expression parseParenthesized() throws Exception{
        if (nextOpenParenthesizeFound())
        {
            index=(nextParenthesizeIndex() + 1);
            Logical result = parseLogical();//start parsing new relation

            if (nextCloseParenthesizeFound()) {
                index += 1;
            }
            return result;
        }

        return null;
    }

    /**
     * Is next char logical opcode?
     * @return
     */
    boolean nextLogicalFound()
    {
        if (lettersRead())
            return false;

        String remain = input.substring(index);
        if(remain.startsWith("and")||remain.startsWith("or")||remain.startsWith("xor")){return true;}

        return false;
    }

    /**
     * Reading logical opcode
     * @return
     */
   public Logical.Opcode readNextLogical(){
        String remain = input.substring(index);
        if(remain.startsWith("and")){
            index+=3;
            return Logical.Opcode.AND;
        }
        else if (remain.startsWith("or")){
            index+=2;
            return Logical.Opcode.OR;
        }

        else if(remain.startsWith("xor")){
            index+=3;
            return Logical.Opcode.XOR;
        }
        return Logical.Opcode.NONE;
    }


    /**
     * Is next char Relation opcode?
     * @return
     */
    boolean nextRelationFound(){
        if (lettersRead()){
            return false;
        }

        String remain = input.substring(index);
        if(remain.startsWith("<")||remain.startsWith("<=")||remain.startsWith(">")||
                remain.startsWith(">=")||remain.startsWith("==")||remain.startsWith("!=")) return true;
        return false;
        }

    /**
     *
     * @return - relation opcode, if it is next
     */
    public Relation.Opcode readNextRelation(){
        String remain = input.substring(index);
        if(remain.startsWith("<")){
            index++;
            return Relation.Opcode.LESS;
        }
        else if (remain.startsWith("<=")){
            index+=2;
            return Relation.Opcode.LESS_EQ;
        }

        else  if (remain.startsWith(">")){
            index++;
            return Relation.Opcode.GREATER;
        }

        else if(remain.startsWith(">=")){
            index+=2;
            return Relation.Opcode.GREATER_EQ;
        }
        else if(remain.startsWith("!=")){
            index+=2;
            return Relation.Opcode.NOT_EQ;
        }
        else if(remain.startsWith("==")){
            index+=2;
            return Relation.Opcode.EQUAL;
        }
        return Relation.Opcode.NONE;
    }

    /**
     * Is next char term opcode?
     * @return
     */
    public boolean nextTermFound(){
        if (lettersRead()){
            return false;
        }

        String remain = input.substring(index);
        if(remain.startsWith("+")||remain.startsWith("-")) return true;
        return false;
    }


    /**
     * Reading term opcode if it is next
     * @return
     */
    public Term.Opcode readNextTerm(){
        String remain = input.substring(index);
        if(remain.startsWith("+")){
            index++;
            return Term.Opcode.PLUS;
        }
        else if (remain.startsWith("-")){
            index++;
            return Term.Opcode.MINUS;
        }
        return Term.Opcode.NONE;
    }

    /**
     * Is next char factor opcode?
     * @return
     */
    public boolean nextFactorFound(){
        if (lettersRead()){
            return false;
        }
        String remain = input.substring(index);
        if(remain.startsWith("*")||remain.startsWith("/")) return true;

        return false;
    }

    public Factor.Opcode readNextFactor(){
        String remain = input.substring(index);
        if(remain.startsWith("*")){
            index++;
            return Factor.Opcode.MULT;
        }
        else if (remain.startsWith("/")){
            index++;
            return Factor.Opcode.DIVIDE;
        }
        return Factor.Opcode.NONE;
    }

    /**
     * Is next char primary digit?
     * @return
     */
    public boolean nextPrimaryFound(){
        return !lettersRead()&&(input.substring(index, index+1).matches("\\d"));
    }


    /**
     * Reading u=int version of primary(not the expression in parenthezises
     * @return
     */
    public int readNextPrimary(){
            String primary = "";
            int curIndex = index;
            while (curIndex<input.length() && input.substring(curIndex, curIndex+1).matches("\\d")){
                primary = primary+input.charAt(curIndex);
                curIndex++;
            }
            index = curIndex;
            return Integer.valueOf(primary);
    }

    //is next char open Parenthesize
    public boolean nextOpenParenthesizeFound(){
        return !lettersRead()&&input.charAt(index)=='(';
    }

    //is next char close Parenthesize
    public boolean nextCloseParenthesizeFound(){
        return  !lettersRead()&&input.charAt(index)==')';
    }

    //index of pointing to open parenthesize
    int nextParenthesizeIndex(){
        if (nextOpenParenthesizeFound()) return index;
        else return -1;
    }
}
