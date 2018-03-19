package expression.exceptions;

import expression.MyExceptions;
import expression.TripleExpression;

/**
 * Created by vadim on 10.04.2017.
 */
public class CheckedSqrt extends UnaryOperator {
    public CheckedSqrt(TripleExpression val) {
        super(val);
    }

    private void check(int a) throws Exception {
        if (a < 0) {
            throw new MyExceptions("sqrt from negative argument");
        }
    }

    protected int apply(int a) throws Exception {
        check(a);
        int l = -1;
        int r = 46341;
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (m*m <= a) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }
}
