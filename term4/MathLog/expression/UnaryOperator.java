package expression;

public class UnaryOperator implements Expression {

    private Expression expression;
    private String operation;

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
}
