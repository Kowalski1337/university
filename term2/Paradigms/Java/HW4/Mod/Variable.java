package expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Variable implements AllExpressions {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return 0;
        }
    }

    public int evaluate(int arg) { return arg; }

    public double evaluate(double arg) {
        return arg;
    }
}