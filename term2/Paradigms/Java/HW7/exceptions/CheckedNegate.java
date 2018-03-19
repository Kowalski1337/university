package expression.exceptions;

import expression.MyExceptions;
import expression.OverflowException;
import expression.TripleExpression;

/**
 * Created by vadim on 03.04.2017.
 */
public class CheckedNegate extends UnaryOperator{
    public CheckedNegate(TripleExpression val) {
        super(val);
    }

    private void check(int a) throws Exception {
        if (a <= Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public int apply(int a) throws Exception {
        check(a);
        return -a;
    }
}