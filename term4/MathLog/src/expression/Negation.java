package expression;

import java.util.HashMap;
import java.util.Objects;

public class Negation implements Expression {

    private Expression negated;
    private Integer hash;

    public Negation(Expression negated) {
        this.negated = negated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negation negation = (Negation) o;
        return Objects.equals(negated, negation.negated);
    }

    @Override
    public String getKey() {
        return "!";
    }

    @Override
    public Expression getLeft() {
        return negated;
    }

    @Override
    public Expression getRight() {
        return negated;
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return !negated.evaluate(values);
    }

    @Override
    public void write(StringBuilder sb) {
        sb.append("!");
        negated.write(sb);
    }

    @Override
    public int hashCode() {
        return hash != null ? hash : (hash = Objects.hash(negated));
    }

    public String toString() {
        return "(!" + negated.toString() + ")";
    }
}
