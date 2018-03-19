package expression;

/**
 * Created by vadim on 25.03.2017.
 */
abstract public class BinaryOperator implements TripleExpression {
    protected TripleExpression arg1;
    protected TripleExpression arg2;

    protected BinaryOperator(TripleExpression a, TripleExpression b){
        arg1 = a;
        arg2 = b;
    }

    public int evaluate(int x, int y, int z){
        return calc(arg1.evaluate(x,y,z),arg2.evaluate(x,y,z));
    }

    abstract public int calc(int x, int y);
}
