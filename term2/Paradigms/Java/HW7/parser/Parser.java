package expression.parser;

import expression.TripleExpression;

/**
 * Created by vadim on 03.04.2017.
 */
public interface Parser {
    TripleExpression parse(String expression) throws Exception;
}
