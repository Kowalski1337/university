package expression;

/**
 * Created by vadim on 25.03.2017.
 */
public class Divide extends BinaryOperator {
    public Divide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    public int calc(int a, int b){
        return a/b;
    }
    
}
