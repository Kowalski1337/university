package expression;

/**
 * Created by vadim on 19.03.2017.
 */
public class Add extends BinaryOperator implements DoubleExpression {
    public Add(DoubleExpression first, DoubleExpression second) {
        arg1 = first;
        arg2 = second;
    }

    public double evaluate(double arg) {
        return arg1.evaluate(arg) + arg2.evaluate(arg);
    }
}
