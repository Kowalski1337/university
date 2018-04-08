import expression.Expression;
import parser.Parser;

public class Tetst {
    public static void main(String[] args) {
        Parser parser = new Parser();

        Expression expression = parser.parse("P->!QQ->!R10&S|!T&U&V");
        StringBuilder sb = new StringBuilder();
        expression.write(sb);
        System.err.println(sb.toString());
    }
}
