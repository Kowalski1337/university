package expression;

public class Variable implements Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void write(StringBuilder sb) {
        sb.append(name);
    }
}
