package expression;

import expression.parser.ExpressionParser;

/**
 * Created by vadim on 25.03.2017.
 */
public class Try {
    public static void main(String[] args) {
        String s = "2-3";
        String t = "2147483647    + 1 ";
        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse(s).evaluate(0, 2, 0));
        //System.out.println(parser.parse(t).evaluate(0, 0, 0));
    }
}
