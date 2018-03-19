package expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Const implements DoubleExpression {
    private int value;

    public Const(int value){
        this.value = value;
    }

    public double evaluate(double arg) {
        return value;
    }
}