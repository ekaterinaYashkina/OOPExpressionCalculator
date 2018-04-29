/**
 * Created by Ekaterina Yashkina on 09-10-2017.
 */
public abstract class Expression {

    protected String tab = "\t";
    public String generateT(String s, int depth){
        for(int i=0; i<depth; i++){
            s+=tab;
        }
        return s;
    }

    public abstract String toJson(int depth);



    /**
     * String representation of expression
     * @param i
     * @return
     */
    public abstract String stringRepr(int i);
    public abstract String calculate();
}
