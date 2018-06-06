package expression;

import java.util.HashMap;
import java.util.Objects;

public class Variable implements Expression {

    private String name;
    private Integer hash;
    //private String key;

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public Variable(String name) {
        this.name = name;
        //key = "var";
        //args = new BinaryOperator[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public String getKey() {
        return "";
    }

    @Override
    public Expression getLeft() {
        return null;
    }

    @Override
    public Expression getRight() {
        return null;
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> values) {
        return values.get(name);
    }

    @Override
    public void write(StringBuilder sb) {
        sb.append(name);
    }

    @Override
    public int hashCode() {
        return hash != null ? hash : (hash = Objects.hash(name));
    }

}
