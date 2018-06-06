package parser;

import expression.*;

public class Parser {

    private static String expr;
    private int currentPos;

    private enum Symbol {VAR, CON, DIS, NEG, IMPL, LBRACKET, RBRACKET}

    private Symbol currentSymbol;
    private String var;

    private char nextChar() {
        if (currentPos < expr.length()) {
            char c = expr.charAt(currentPos);
            currentPos++;
            return c;
        } else {
            return '\0';
        }
    }

    private void getOp() {
        char c = nextChar();
        switch (c) {
            case '-':
                currentPos++;
                currentSymbol = Symbol.IMPL;
                break;
            case '&':
                currentSymbol = Symbol.CON;
                break;
            case '|':
                currentSymbol = Symbol.DIS;
                break;
            case '!':
                currentSymbol = Symbol.NEG;
                break;
            case '(':
                currentSymbol = Symbol.LBRACKET;
                break;
            case ')':
                currentSymbol = Symbol.RBRACKET;
                break;
            default:
                currentSymbol = Symbol.VAR;
                String v = "";
                v += c;
                while ((c = nextChar()) != '\0' && (Character.isDigit(c) || Character.isLetter(c))) {
                    v += c;
                }
                currentPos--;
                var = v;
        }
    }

    private Expression highPriority() {
        getOp();
        Expression t;
        switch (currentSymbol) {
            case VAR:
                t = new Variable(var);
                getOp();
                break;
            case NEG:
                t = new Negation(highPriority());
                break;
            case LBRACKET:
                t = getExpression();
                getOp();
                break;
            default:
                t = null;
        }
        return t;
    }

    private Expression con() {
        Expression left = highPriority();
        while(true) {
            switch (currentSymbol) {
                case CON:
                    left = new Conjunction(left, highPriority());
                    break;
                default:
                    return left;
            }
        }
    }

    private Expression dis() {
        Expression left = con();
        while (true) {
            switch (currentSymbol) {
                case DIS:
                    left = new Disjunction(left, con());
                    break;
                default:
                    return left;
            }
        }
    }

    private Expression getExpression() {
        Expression left = dis();
        while (true) {
            switch (currentSymbol) {
                case IMPL:
                    left = new Implication(left, getExpression());
                    break;
                default:
                    return left;
            }
        }
    }
    public Expression parse(String str) {
        currentPos = 0;
        expr = str;
        currentPos = 0;
        return getExpression();
    }

//    public static String removeWhitespaces(String s) {
//        StringBuilder ss = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//            if (!Character.isWhitespace(s.charAt(i))) ss.append(s.charAt(i));
//        }
//        return ss.toString();
//    }
//
//    public static Expression getExp(String s) {
//        return new ExpressionParser().parse(removeWhitespaces(s));
//    }
}