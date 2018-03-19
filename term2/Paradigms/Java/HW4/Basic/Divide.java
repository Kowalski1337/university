package expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Divide extends BinaryOperator implements DoubleExpression {
    public Divide(DoubleExpression first, DoubleExpression second) {
        arg1 = first;
        arg2 = second;
    }

    public double evaluate(double arg) {
        double l = arg2.evaluate(arg);
        assert l != 0: "division by zero isn't allowed";
        return arg1.evaluate(arg) / l;
    }
}
