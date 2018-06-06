package expression;

import java.util.HashMap;

public interface Expression {
    void write(StringBuilder sb);

    int hashCode();

    boolean equals(Object obj);

    String getKey();

    Expression getLeft();

    Expression getRight();

    boolean evaluate(HashMap<String, Boolean> values);
}
