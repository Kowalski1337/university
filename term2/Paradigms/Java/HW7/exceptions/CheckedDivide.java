package expression.exceptions;

import expression.MyExceptions;
import expression.OverflowException;
import expression.TripleExpression;

/**
 * Created by vadim on 03.04.2017.
 */
public class CheckedDivide extends BinaryOperator implements TripleExpression {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected void check(int a, int b) throws Exception {
        if(a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException();
        }
        if (b == 0) {
            throw new MyExceptions("division by zero");
        }
    }
    protected int apply(int a, int b) throws Exception {
        check(a, b);
        return a / b;
    }
}