package expression;

/**
 * Created by vadim on 27.03.2017.
 */
public class RightShift extends BinaryOperator {
    public RightShift(TripleExpression first, TripleExpression second){
        super(first,second);
    }

    public int calc(int a, int b){
        return a >> b;
    }
}
