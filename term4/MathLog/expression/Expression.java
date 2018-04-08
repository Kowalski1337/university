package expression;

public interface Expression {
    void write(StringBuilder sb);
    Expression getRight();
    Expression getLeft();
    int getType();
    int hash();
}
