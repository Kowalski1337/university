package expression;


/**
 * Created by vadim on 20.03.2017.
 */
public class Subtract extends BinaryOperator implements DoubleExpression {
    public Subtract(DoubleExpression first, DoubleExpression second) {
        arg1 = first;
        arg2 = second;
    }

    public double evaluate(double arg) {
        return arg1.evaluate(arg) - arg2.evaluate(arg);
    }
}
