package expression.exceptions;

import expression.MyExceptions;
import expression.TripleExpression;

/**
 * Created by vadim on 03.04.2017.
 */
public class CheckedLog2 extends UnaryOperator {

    public CheckedLog2(TripleExpression value){
        super(value);
    }

    private void check(int a) throws Exception{
        if (a <= 0){
            throw new MyExceptions("log2 from notpositive argument");
        }
    }

    protected int apply(int a) throws Exception{
        check(a);
        int log = 0;
        while ((a >> 1) != 0){
            log++;
            a /= 2;
        }
        return log;
    }
}
