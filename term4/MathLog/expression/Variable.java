package expression;

public class Variable implements Expression {
    private String name;
    private Integer hasCode;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void write(StringBuilder sb) {
        sb.append(name);
    }

    @Override
    public Expression getRight() {
        return null;
    }

    @Override
    public Expression getLeft() {
        return null;
    }

    @Override
    public int getType() {
        return GetType.getType("variable");
    }

    @Override
    public int hash() {
        return hasCode != null ? hasCode : (hasCode = ("v:" + name).hashCode());
    }


}
