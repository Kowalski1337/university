package expression;

public abstract class BinaryOperator implements Expression {
    Expression left;
    Expression right;
    private String key;
    Integer hash;
    private StringBuilder strImpl;

    BinaryOperator(Expression left, Expression right, String key){
        this.right = right;
        this.left = left;
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    @Override
    public Expression getRight() {
        return right;
    }

    @Override
    public Expression getLeft() {
        return left;
    }


    @Override
    public String toString() {
        if (strImpl == null) {
            write(strImpl = new StringBuilder());
        }
        return strImpl.toString();
    }

    public void write(StringBuilder sb){
        sb.append('(');
        left.write(sb);
        sb.append(key);
        right.write(sb);
        sb.append(')');
    }

    /*@Override
    public boolean equals(Object obj) {
        return obj != null && hashCode() == obj.hashCode() && toString().equals(toString());
    }*/
}
