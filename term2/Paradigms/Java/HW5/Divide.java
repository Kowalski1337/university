package expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Divide extends BinaryOperator {
    public Divide(AllExpressions first, AllExpressions second) {
        super(first, second);
    }

    public int calc(int a, int b) {
        return a / b;
    }

    public double calcd(double a, double b) {
        return a / b;
    }

}
