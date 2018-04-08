package expression;

public class UnaryOperator implements Expression {

    private Expression expression;
    private String operation;
    private Integer hashCode;

    public UnaryOperator(Expression expression, String operation) {
        this.expression = expression;
        this.operation = operation;
    }


    @Override
    public void write(StringBuilder sb) {
        sb.append('(');
        sb.append(operation);
        expression.write(sb);
        sb.append(')');
    }

    @Override
    public Expression getRight() {
        return expression;
    }

    @Override
    public Expression getLeft() {
        return expression;
    }

    @Override
    public int getType() {
        return GetType.getType(operation);
    }

    @Override
    public int hash() {
        return hashCode != null ? hashCode : (hashCode = ("(" + GetType.getType(operation) + expression.hash() + ")").hashCode());
    }
}
