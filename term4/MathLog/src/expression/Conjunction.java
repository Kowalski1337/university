package expression;

import java.util.HashMap;
import java.util.Objects;

public class Conjunction extends BinaryOperator {

    public boolean evaluate(HashMap<String, Boolean> values) {
        return getLeft().evaluate(values) && getRight().evaluate(values);
    }


    public Conjunction(Expression left, Expression right) {
        super(left, right, "&");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conjunction that = (Conjunction) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return hash != null ? hash : (hash = Objects.hash(left, right));
    }

}
