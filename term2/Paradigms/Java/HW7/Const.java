package expression;

/**
 * Created by vadim on 03.04.2017.
 */
public class Const implements TripleExpression {
    private int value;
    public Const(int val) {
        value = val;
    }

    public int evaluate(int x, int y, int z) {
        return value;
    }
}
