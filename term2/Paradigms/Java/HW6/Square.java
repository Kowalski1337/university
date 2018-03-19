package expression;

/**
 * Created by vadim on 01.04.2017.
 */
public class Square implements TripleExpression{
    private TripleExpression value;
    public Square(TripleExpression value){
        this.value = value;
    }

    public int evaluate(int x, int y, int z){
        int cur = value.evaluate(x, y, z);
        return cur*cur;
    }
}
