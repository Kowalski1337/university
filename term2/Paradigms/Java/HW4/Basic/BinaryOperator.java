package expression;

/**
 * Created by vadim on 20.03.2017.
 */
abstract public class BinaryOperator implements DoubleExpression {
    protected DoubleExpression arg1;
    protected DoubleExpression arg2;

    abstract public double evaluate(double arg);
}