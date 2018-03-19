package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by vadim on 03.04.2017.
 */
abstract public class BinaryOperator implements TripleExpression {
    private TripleExpression fOp;
    private TripleExpression sOp;

    BinaryOperator(TripleExpression first, TripleExpression second) {
        fOp = first;
        sOp = second;
    }

    abstract protected int apply(int a, int b) throws Exception;

    public int evaluate(int x, int y, int z) throws Exception {
        return apply(fOp.evaluate(x, y, z), sOp.evaluate(x, y, z));
    }
}
