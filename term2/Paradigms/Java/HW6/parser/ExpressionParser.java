package expression.parser;

import expression.*;

/**
 * Created by vadim on 25.03.2017.
 */
public class ExpressionParser implements Parser {
    private int index = 0;
    private String expression;
    private int constant;
    private char variable;

    private enum State {number, plus, minus, mul, div, lbracket, rbracket, variable, lshift, rshift, abs, square}

    private State current;

    private char getNextChar() {
        if (index < expression.length()) {
            char ret = expression.charAt(index);
            index++;
            return ret;
        } else {
            return '&';
        }
    }

    private void getNext() {
        while (true) {
            if (!Character.isWhitespace(getNextChar())) {
                break;
            }
        }
        index--;
        char ch = getNextChar();
        if (Character.isDigit(ch)) {
            StringBuilder str = new StringBuilder();
            while (Character.isDigit(ch)) {
                str.append(ch);
                ch = getNextChar();
            }
            index--;
            constant = Integer.parseInt(str.toString());

            current = State.number;

        } else {
            switch (ch) {
                case '-':
                    current = State.minus;
                    break;
                case '+':
                    current = State.plus;
                    break;
                case '*':
                    current = State.mul;
                    break;
                case '/':
                    current = State.div;
                    break;
                case '(':
                    current = State.lbracket;
                    break;
                case ')':
                    current = State.rbracket;
                    break;
                case 'x':
                case 'y':
                case 'z':
                    current = State.variable;
                    variable = ch;
                    break;
                case '<':
                    current = State.lshift;
                    index++;
                    break;
                case '>':
                    current = State.rshift;
                    index++;
                    break;
                case 's':
                    current = State.square;
                    index += 5;
                    break;
                case 'a':
                    current = State.abs;
                    index += 2;
                    break;
            }
        }
    }

    private TripleExpression unary() {
        getNext();
        TripleExpression ret = new Const(1);
        switch (current) {
            case number:
                ret = new Const(constant);
                getNext();
                break;

            case variable:
                ret = new Variable(Character.toString(variable));
                getNext();
                break;

            case minus:
                ret = new Subtract(new Const(0), unary());
                break;

            case lbracket:
                ret = shifts();
                getNext();
                break;

            case abs:
                ret = new Abs(unary());
                break;

            case square:
                ret = new Square(unary());
                break;



        }
        return ret;
    }

    private TripleExpression mulDiv() {
        TripleExpression cur = unary();
        while (true) {
            switch (current) {
                case mul:
                    cur = new Multiply(cur, unary());
                    break;

                case div:
                    cur = new Divide(cur, unary());
                    break;

                default:
                    return cur;
            }
        }
    }

    private TripleExpression addSubt() {
        TripleExpression cur = mulDiv();
        while (true) {
            switch (current) {
                case minus:
                    cur = new Subtract(cur, mulDiv());
                    break;

                case plus:
                    cur = new Add(cur, mulDiv());
                    break;

                default:
                    return cur;
            }
        }
    }

    private TripleExpression shifts() {
        TripleExpression cur = addSubt();
        while (true) {
            switch (current) {
                case lshift:
                    cur = new LeftShift(cur, addSubt());
                    break;
                case rshift:
                    cur = new RightShift(cur, addSubt());
                    break;
                default:
                    return cur;
            }
        }
    }

    public TripleExpression parse(String expression) {
        this.expression = expression;
        TripleExpression res = shifts();
        index = 0;
        return res;
    }

}
