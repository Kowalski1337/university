import expression.Expression;
import expression.Implication;
import expression.Variable;
import javafx.util.Pair;
import parser.Parser;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class HW2 {
    private static StringBuilder ans = new StringBuilder();
    private static Parser parser = new Parser();
    private static Expression[] axioms = new Expression[]{
            parser.parse("A->B->A"),
            parser.parse("(A->B)->(A->B->C)->(A->C)"),
            parser.parse("A->B->A&B"),
            parser.parse("A&B->A"),
            parser.parse("A&B->B"),
            parser.parse("A->A|B"),
            parser.parse("B->A|B"),
            parser.parse("(A->C)->(B->C)->(A|B->C)"),
            parser.parse("(A->B)->(A->!B)->!A"),
            parser.parse("!!A->A")
    };

    private static HashMap<Expression, Integer> hyp = new HashMap<>(52001, 1);
    private static HashMap<Expression, Integer> mentioned = new HashMap<>(52001, 1);
    private static HashMap<Expression, Pair<Expression, Expression>> mpProved = new HashMap<>(52001, 1);
    private static HashMap<Expression, Expression> vars = new HashMap<>(52001, 1);
    private static ArrayList<Expression> expressions = new ArrayList<>(10000);
    private static Expression alpha;

    private static boolean compareTrees(Expression expr, Expression to, HashMap<Expression, Expression> vars) {
        if (to instanceof Variable) {
            if (vars.containsKey(to)) {
                return vars.get(to).equals(expr);
            } else {
                vars.put(to, expr);
                return true;
            }
        } else if (to.getKey().equals(expr.getKey())) {
            return compareTrees(expr.getLeft(), to.getLeft(), vars) && compareTrees(expr.getRight(), to.getRight(), vars);
        }
        return false;
    }

    private static Pair<Integer, Integer> getMp(Expression expr) {
        Pair<Expression, Expression> a = mpProved.get(expr);
        if (a == null) {
            return null;
        }
        Integer b = mentioned.get(a.getValue());
        if (b == null) {
            return null;
        }
        return new Pair<>(mentioned.get(a.getKey()), b);
    }

    private static void writeExpression(Expression expr) {
        expr.write(ans);
        ans.append('\n');
    }

    private static void firstCase(Expression expr) {
        Implication ae = new Implication(alpha, expr);
        writeExpression(expr);
        writeExpression(new Implication(expr, ae));
        writeExpression(ae);
    }

    private static void secondCase() {
        Implication aia = new Implication(alpha, alpha);
        writeExpression(new Implication(alpha, aia));
        writeExpression(new Implication(new Implication(alpha, aia), new Implication(new Implication(alpha, new Implication(aia, alpha)), aia)  ));
        writeExpression(new Implication(new Implication(alpha, new Implication(aia, alpha)), aia));
        writeExpression(new Implication(alpha, new Implication(aia, alpha)));
        writeExpression(aia);
    }

    private static void thirdCase(Expression bi, Expression bj) {
        writeExpression(new Implication(new Implication(alpha, bj), new Implication(new Implication(alpha, new Implication(bj, bi)), new Implication(alpha, bi))));
        writeExpression(new Implication(new Implication(alpha, new Implication(bj, bi)), new Implication(alpha, bi)));
        writeExpression(new Implication(alpha, bi));
    }



    private static void getHint(Expression expr) {
        if ("->".equals(expr.getKey())) {
            mpProved.put(expr.getRight(), new Pair<>(expr, expr.getLeft()));
        }
        Integer h = hyp.get(expr);
        if (h != null) {
            firstCase(expr);
            return;
        }
        for (Expression axiom : axioms) {
            vars.clear();
            if (compareTrees(expr, axiom, vars)) {
                firstCase(expr);
                return;
            }
        }
        if (alpha.equals(expr)) {
            secondCase();
            return;
        }
        Pair<Integer, Integer> mpResult = getMp(expr);
        assert mpResult != null;
        thirdCase(expr, expressions.get(mpResult.getValue()));
    }

    public static void main(String[] args) throws Exception {
        BufferedWriter out = Files.newBufferedWriter(Paths.get("output.txt"), Charset.forName("UTF-8"));
        try (BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"), Charset.forName("UTF-8"))) {
            String[] header = r.readLine().split("\\|-");
            ArrayList<Expression> hypArr = new ArrayList<>(1000);
            if (!header[0].isEmpty()) {
                String[] dataStrings = header[0].split(",");
                for (int i = 0; i < dataStrings.length - 1; i++) {
                    hyp.put(alpha = parser.parse(dataStrings[i]), i);
                    hypArr.add(alpha);
                }
                alpha = parser.parse(dataStrings[dataStrings.length - 1]);
            }
            Expression resultRight = parser.parse(header[1]);
            Expression result = new Implication(alpha, resultRight);
            int counter = 1;
            String line;

            for (int i = 0; i < hypArr.size(); i++) {
                hypArr.get(i).write(ans);
                ans.append( i == hypArr.size() - 1 ? "" : ",");
            }
            ans.append("|-");
            result.write(ans);
            ans.append('\n');
            while ((line = r.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                Expression cur = parser.parse(line);
                getHint(cur);
                mentioned.put(cur, counter - 1);
                expressions.add(cur);
                counter++;
            }
        }
        out.write(ans.toString());
        out.close();

    }
}