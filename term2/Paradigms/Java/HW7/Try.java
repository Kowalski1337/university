package expression;

import expression.parser.ExpressionParser;

/**
 * Created by vadim on 03.04.2017.
 */
public class Try {
    public static void main(String[] args) throws Exception {
        try {
            ExpressionParser parser = new ExpressionParser();
            String s = "--25";
            System.out.println(parser.parse(s).evaluate(0, 0, 0));
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
