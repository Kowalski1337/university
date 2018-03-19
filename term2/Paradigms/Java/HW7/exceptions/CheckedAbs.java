package expression.exceptions;

import expression.OverflowException;
import expression.TripleExpression;

/**
 * Created by vadim on 10.04.2017.
 */
public class CheckedAbs extends UnaryOperator {

    public CheckedAbs(TripleExpression value){
        super(value);
    }

    private void check(int a) throws Exception{
        if (a == Integer.MIN_VALUE){
            throw new OverflowException();
        }
    }

    protected int apply(int a) throws Exception{
        check(a);
        return a >= 0 ? a: -a;
    }
}
