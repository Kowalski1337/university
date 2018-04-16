import expression.Expression;
import expression.Variable;
import fastscanner.FastScanner;
import javafx.util.Pair;
import parser.Parser;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class HW1 {
    private static StringBuilder ans = new StringBuilder();
    public static Parser parser = new Parser();
    private static Expression[] axioms = new Expression[] {
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

    public static Integer isAxiom(Expression e) {
        int num = 1;
        for (Expression s : axioms) {
            vars.clear();
            if (compareTrees(s, e, vars)) {
                return num;
            }
            num++;
        }
        return null;
    }
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

    private static void getHint(Expression expr, BufferedWriter out) throws IOException {
        if ("->".equals(expr.getKey())) {
            mpProved.put(expr.getRight(), new Pair<>(expr, expr.getLeft()));
        }
        Integer h = hyp.get(expr);
        if (h != null) {
            out.write("Предп. " + (h + 1));
            return;
        }
        for (int i = 0; i < axioms.length; i++) {
            vars.clear();
            if (compareTrees(expr, axioms[i], vars)) {
                out.write("Сх. акс. " + (i + 1));
                return;
            }
        }
        Pair<Integer, Integer> mpResult = getMp(expr);
        out.write(mpResult == null ? "Не доказано" : "M.P. " + (mpResult.getKey() + 1) + ", " + (mpResult.getValue() + 1));
    }

    public static void main(String[] args) throws Exception {
        try (FastScanner scanner = new FastScanner(new File("input.txt"))) {
            String[] header = scanner.next().split("\\|-");
            if (!header[0].isEmpty()) {
                String[] dataStrings = header[0].split(",");
                for (int i = 0; i < dataStrings.length; i++) {
                    hyp.put(parser.parse(dataStrings[i]), i);
                }
            }
            int counter = 1;
            String line;
            try (BufferedWriter out = Files.newBufferedWriter(Paths.get("output.txt"), Charset.forName("UTF-8"))) {
                while ((line = scanner.next()) != null) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    Expression cur = parser.parse(line);
                    out.write("(" + counter + ") " + line + " (");
                    getHint(cur, out);
                    out.write(")");
                    out.newLine();
                    mentioned.put(cur, counter - 1);
                    counter++;
                }
            }
        }
    }
}
