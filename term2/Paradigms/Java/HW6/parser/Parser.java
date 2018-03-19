package expression.parser;


import expression.TripleExpression;

/**
 * Created by vadim on 25.03.2017.
 */
public interface Parser {
    TripleExpression parse(String expression);
}
