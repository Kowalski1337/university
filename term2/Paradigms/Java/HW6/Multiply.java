package expression;

/**
 * Created by vadim on 25.03.2017.
 */
public class Multiply extends BinaryOperator {
    public Multiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public int calc(int a, int b){
        return a*b;
    }
}
