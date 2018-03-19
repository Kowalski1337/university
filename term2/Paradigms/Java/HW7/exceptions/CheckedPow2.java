package expression.exceptions;

import expression.MyExceptions;
import expression.OverflowException;
import expression.TripleExpression;

/**
 * Created by vadim on 03.04.2017.
 */
public class CheckedPow2 extends UnaryOperator{

    public CheckedPow2(TripleExpression value){
        super(value);
    }

    private void check(int a) throws Exception{
        if (a >=31){
            throw new OverflowException();
        }
        if (a < 0){
            throw new MyExceptions("pow2 from negative argument");
        }
    }

    protected int apply(int a) throws Exception{
        check(a);
        int pow = 1;
        for (int i = 0; i < a; i++){
            pow <<= 1;
        }
        return pow;
    }
}
