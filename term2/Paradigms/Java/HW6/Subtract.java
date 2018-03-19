package expression;

/**
 * Created by vadim on 25.03.2017.
 */
public class Subtract extends BinaryOperator {
    public Subtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public int calc(int a, int b){
        return a-b;
    }
}
