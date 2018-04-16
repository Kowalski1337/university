package parser;

import expression.*;

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
                cur = Token.VAR;
            }
        }
    }

    private Expression parseUnary() {
        getNext();
        Expression ans;
        switch (cur) {
            case NOT:
                ans = new Negation(parseUnary());
                break;
            case VAR:
                ans = new Variable(variable);
                getNext();
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
                ans = new Conjunction(ans, parseUnary());
            } else {
                return ans;
            }
        }
    }

    Expression parseOr() {
        Expression ans = parseAnd();
        while (true) {
            if (cur == Token.OR) {
                ans = new Disjunction(ans, parseAnd());
            } else {
                return ans;
            }
        }
    }

    Expression parseImpl() {
        Expression ans = parseOr();
        while (true) {
            if (cur == Token.IMPL) {
                ans = new Implication(ans, parseImpl());
            } else {
                return ans;
            }
        }
    }

    public  Expression parse(String expression) {
        this.expression = expression;
        Expression ans = parseImpl();
        index = 0;
        return ans;
    }
}
