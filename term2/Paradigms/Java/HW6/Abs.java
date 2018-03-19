package expression;

/**
 * Created by vadim on 01.04.2017.
 */
public class Abs implements TripleExpression{
    private TripleExpression value;

    public Abs(TripleExpression value){
        this.value = value;
    }

    public int evaluate(int x, int y, int z){
        return Math.abs(value.evaluate(x, y, z));
    }
}
