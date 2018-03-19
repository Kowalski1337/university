package expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Const implements AllExpressions {
    private int value1;
    private double value2;

    public Const(int value) {
        value1 = value;
        value2 = value;
    }

    public Const(double value) {
        value2 = value;
        value1 = (int) value;
    }

    public int evaluate(int arg) {
        return value1;
    }

    public int evaluate(int x, int y, int z) {
        return value1;
    }

    public double evaluate(double arg) {
        return value2;
    }
}