package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by vadim on 16.04.2017.
 */
abstract public class UnaryOperator implements TripleExpression {
    private TripleExpression fOp;

    UnaryOperator(TripleExpression first) {
        fOp = first;
    }

    abstract protected int apply(int a) throws Exception;

    public int evaluate(int x, int y, int z) throws Exception {
        return apply(fOp.evaluate(x, y, z));
    }
}
