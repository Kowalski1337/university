package expression;

public class BinaryOperator implements Expression {
    Expression first;
    Expression second;
    String operation;

    public BinaryOperator(Expression first, Expression second, String operation) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }


    public void write(StringBuilder sb) {
        sb.append('(');
        sb.append(operation);
        sb.append(',');
        first.write(sb);
        sb.append(',');
        second.write(sb);
        sb.append(')');
    }
}
