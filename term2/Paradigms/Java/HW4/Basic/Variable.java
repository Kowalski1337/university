package expression;

import expression.Expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Variable implements Expression {
    private String name;
    public Variable(String name) {
        this.name = name;
    }

    public double evaluate(double arg) {
        return arg;
    }
}