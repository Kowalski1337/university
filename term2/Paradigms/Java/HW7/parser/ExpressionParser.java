package expression.parser;

import expression.*;
import expression.exceptions.*;

import java.util.LinkedList;

/**
 * Created by vadim on 03.04.2017.
 */
public class ExpressionParser implements Parser {
    private int index;
    private String expression;
    private int constant;
    private char VARIABLE;
    private boolean wasOp;

    private enum State {NUMBER, PLUS, UNARYMINUS, BINARYMINUS, MUL, DIV, LBRACKET, RBRACKET, VARIABLE, POW2, LOG2, ABS, SQRT, MIN, MAX}

    private State current;

    private void error(int i, String mesage) throws Exception {
        StringBuilder st = new StringBuilder();

        for (int j = 0; j < i; j++) {
            st.append(" ");
        }

        st.append("^");

        throw new MyExceptions(mesage + "\n" + expression + "\n" + st.toString());
    }

    private void miss(boolean can, int dl) throws Exception {
        if (can) {
            wasOp = true;
        }else{
            error(index - dl,"missing prev operand");
        }
    }

    private char getNextChar() {
        if (index < expression.length()) {
            char ret = expression.charAt(index++);
            return ret;
        } else {
            return '!';
        }
    }

    private void parseInt(char ch, int dl) throws Exception{
        int  f = index - dl, l = index - 1;
        while (Character.isDigit(ch)) {
            l++;
            ch = getNextChar();
        }
        index--;
        try {
            constant = Integer.parseInt(expression.substring(f, l));
        } catch (NumberFormatException e) {
            throw new MyExceptions("constant overflow " + expression.substring(f, l));
        }
        current = State.NUMBER;
        wasOp = false;
    }


    private void getNext() throws Exception {
        if (index >= expression.length() && wasOp) {
            error(index, "missing next operand");
        }
        while (Character.isWhitespace(getNextChar())) {

        }
        index--;
        char ch = getNextChar();
        if (Character.isDigit(ch)) {
            parseInt(ch, 1);
        } else {
            switch (ch) {
                case '-':
                    if (!wasOp) {
                        wasOp = true;
                        current = State.BINARYMINUS;
                    } else {
                        char s = expression.charAt(index);
                        if (Character.isDigit(s)){
                            parseInt(getNextChar(), 2);
                            current = State.NUMBER;
                            wasOp = false;
                        } else {
                            current = State.UNARYMINUS;
                        }
                    }
                    break;
                case '+':
                    miss(!wasOp, 2);
                    current = State.PLUS;
                    break;
                case '*':
                    miss(!wasOp, 2);
                    current = State.MUL;
                    break;
                case '/':
                    miss(!wasOp, 2);
                    current = State.DIV;
                    break;
                case ')':
                    miss(!wasOp, 2);
                    wasOp = false;
                    current = State.RBRACKET;
                    break;
                case '(':
                    current = State.LBRACKET;
                    break;
                case 'x':
                case 'y':
                case 'z':
                    current = State.VARIABLE;
                    wasOp = false;
                    VARIABLE = ch;
                    break;
                case 'l':
                case 'p':
                case 's':
                case 'a':
                case 'm':
                    int f = index - 1;
                    while (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
                        ch = getNextChar();
                    }
                    if (index != expression.length()){
                        index--;
                    }
                    String s = expression.substring(f, index);
                    switch (s) {
                        case "log2":
                            miss(wasOp, 5);
                            current = State.LOG2;
                            break;
                        case "pow2":
                            miss(wasOp, 5);
                            current = State.POW2;
                            break;
                        case "sqrt":
                            miss(wasOp, 5);
                            current = State.SQRT;
                            break;
                        case "abs":
                            miss(wasOp, 4);
                            current = State.ABS;
                            break;
                        case "min":
                            miss(!wasOp, 4);
                            current = State.MIN;
                            break;
                        case "max":
                            miss(!wasOp, 4);
                            current = State.MAX;
                            break;

                        default:
                            error(f, "Unknown identifier");
                    }
                    break;


                default:
                    if (!Character.isWhitespace(ch))
                        error(index - 1, "Unexpected symbol");
            }
        }
    }


    private TripleExpression unary() throws Exception {
        getNext();
        TripleExpression ret;
        switch (current) {
            case NUMBER:
                ret = new Const(constant);
                getNext();
                break;

            case VARIABLE:
                ret = new Variable(Character.toString(VARIABLE));
                getNext();
                break;

            case UNARYMINUS:
                ret = new CheckedNegate(unary());
                break;

            case ABS:
                ret = new CheckedAbs(unary());
                break;

            case LBRACKET:
                ret = minMax();
                getNext();
                break;

            case POW2:
                ret = new CheckedPow2(unary());
                break;

            case LOG2:
                ret = new CheckedLog2(unary());
                break;

            case SQRT:
                ret = new CheckedSqrt(unary());
                break;

            default:
                ret = null;
        }
        return ret;
    }

    private TripleExpression MULDIV() throws Exception {
        TripleExpression left = unary();
        while (true) {
            switch (current) {
                case MUL:
                    left = new CheckedMultiply(left, unary());
                    break;

                case DIV:
                    left = new CheckedDivide(left, unary());
                    break;

                default:
                    return left;
            }
        }
    }

    private TripleExpression addSubt() throws Exception {
        TripleExpression left = MULDIV();
        while (true) {
            switch (current) {
                case BINARYMINUS:
                    left = new CheckedSubtract(left, MULDIV());
                    break;

                case PLUS:
                    left = new CheckedAdd(left, MULDIV());
                    break;

                default:
                    return left;
            }
        }
    }

    private TripleExpression minMax() throws Exception {
        TripleExpression left = addSubt();
        while (true) {
            switch (current) {
                case MIN:
                    left = new CheckedMin(left, addSubt());
                    break;
                case MAX:
                    left = new CheckedMax(left, addSubt());
                    break;
                default:
                    return left;
            }
        }
    }

    public TripleExpression parse(String expr) throws Exception {
        expression = expr;
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                stack.add(i);
            } else if (expression.charAt(i) == ')') {
                if (stack.size() == 0) {
                    error(i, "Missing open parenthesis");
                }
                stack.removeLast();
            }
        }
        if (stack.size() != 0) {
            int l = stack.peekLast();
            error(l, "Missing close parenthesis");
        }
        index = 0;
        wasOp = true;
        return minMax();
    }
}
