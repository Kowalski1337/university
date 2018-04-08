package parser;

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
                c
        }
    }
}
