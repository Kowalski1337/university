package expression;

import java.util.HashMap;
import java.util.Objects;

public class Implication extends BinaryOperator {


    public Implication(Expression left, Expression right) {
        super(left, right, "->");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Implication that = (Implication) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return !getLeft().evaluate(values) || getRight().evaluate(values);
    }

    @Override
    public int hashCode() {
        return hash != null ? hash : (hash = Objects.hash(left, right));
    }


}
