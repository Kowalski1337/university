package expression;

public class BinaryOperator implements Expression{
    private Expression first;
    private Expression second;
    private String operation;
    private Integer hashCode;

    public BinaryOperator(Expression first, Expression second, String operation){
        this.operation = operation;
        this.first = first;
        this.second = second;
    }

    @Override
    public void write(StringBuilder sb) {
        sb.append('(');
        sb.append(operation);
        sb.append(',');
        first.write(sb);
        sb.append(',');
        second.write(sb);
        sb.append(')');
    }

    @Override
    public Expression getRight() {
        return first;
    }

    @Override
    public Expression getLeft() {
        return second;
    }

    @Override
    public int getType() {
        return GetType.getType(operation);
    }

    @Override
    public int hash() {
        return (hashCode != null) ? hashCode : (hashCode = ("(" + GetType.getType(operation) + ", " + first.hash() + ", " + first.hash() + ")").hashCode());
    }
}
