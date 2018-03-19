package expression;

/**
 * Created by vadim on 03.04.2017.
 */
public class Variable implements TripleExpression {
    private String name;
    public Variable(String nam) {
        name = nam;
    }

    public int evaluate(int x, int y, int z) {
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}