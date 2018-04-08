package parser;

import expression.BinaryOperator;
import expression.Expression;
import expression.UnaryOperator;
import expression.Variable;

public class Parser {
    int index = 0;
    private String expression;
    Token cur;
    private String variable;

    private enum Token {AND, OR, IMPL, VAR, LPAR, RPAR, NOT}

    private char getNextChar() {
        if (index < expression.length()) {
            return expression.charAt(index++);
        } else {
            return '`';
        }
    }

    private void getNext() {
        char ch = getNextChar();
        switch (ch) {
            case '&':
                cur = Token.AND;
                break;
            case '|':
                cur = Token.OR;
                break;
            case '-':
                cur = Token.IMPL;
                index++;
                break;
            case '(':
                cur = Token.LPAR;
                break;
            case ')':
                cur = Token.RPAR;
                break;
            case '!':
                cur = Token.NOT;
                break;
            default: {
                StringBuilder sb = new StringBuilder();
                while (Character.isLetterOrDigit(ch)) {
                    sb.append(ch);
                    ch = getNextChar();
                }
                index--;
                variable = sb.toString();
                index--;
            }
        }
    }

    private Expression parseUnary() {
        getNext();
        Expression ans;
        switch (cur) {
            case NOT:
                ans = new UnaryOperator(parseUnary(), "!");
                break;
            case VAR:
                ans = new Variable(variable);
                break;
            case LPAR:
                ans = parseImpl();
                getNext();
                break;
            default:
                ans = null;
        }
        return ans;
    }

    Expression parseAnd() {
        Expression ans = parseUnary();
        while (true) {
            if (cur == Token.AND) {
                ans = new BinaryOperator(ans, parseUnary(), "&");
            } else {
                return ans;
            }
        }
    }

    Expression parseOr() {
        Expression ans = parseAnd();
        while (true) {
            if (cur == Token.OR) {
                ans = new BinaryOperator(ans, parseAnd(), "|");
            } else {
                return ans;
            }
        }
    }

    Expression parseImpl() {
        Expression ans = parseOr();
        while (true) {
            if (cur == Token.IMPL) {
                ans = new BinaryOperator(ans, parseOr(), "->");
            } else {
                return ans;
            }
        }
    }

    public Expression parse(String expression) {
        this.expression = expression;
        Expression ans = parseImpl();
        index = 0;
        return ans;
    }
}
