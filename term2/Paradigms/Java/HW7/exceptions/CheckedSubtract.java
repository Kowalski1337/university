package expression.exceptions;

import expression.MyExceptions;
import expression.OverflowException;
import expression.TripleExpression;

/**
 * Created by vadim on 03.04.2017.
 */
public class CheckedSubtract extends BinaryOperator implements TripleExpression {
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected void check(int a, int b) throws Exception {
        if(b > 0 && a < Integer.MIN_VALUE + b || b < 0 && a > Integer.MAX_VALUE + b) {
            throw new OverflowException();
        }
    }

    protected int apply(int a, int b) throws Exception {
        check(a, b);
        return a - b;
    }
}
