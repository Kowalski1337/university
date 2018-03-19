package expression.exceptions;

import expression.TripleExpression;

/**
 * Created by vadim on 10.04.2017.
 */
public class CheckedMin extends BinaryOperator {
    public CheckedMin(TripleExpression f, TripleExpression s){
        super(f,s);
    }
    protected int apply(int a, int b) throws Exception{
        if (a > b) return b;
        return a;
    }
}
