import expression.Expression;
import fastscanner.FastScanner;
import parser.Parser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

class HW1 {
    private static final Map<Integer, Integer> assumptions = new HashMap<>();

    private static final Map<Integer, List<Expression>> forr = new HashMap<>();
    private static final Map<Integer, Integer> firstpos = new HashMap<>();


    private static Parser parser = new Parser();
    private static final StringBuilder sb = new StringBuilder();
    private static Expression[] e_axioms = {
            parser.parse("A->(B->A)"),
            parser.parse("(A->B)->(A->B->C)->(A->C)"),
            parser.parse("A->B->A&B"),
            parser.parse("A&B->A"),
            parser.parse("A&B->B"),
            parser.parse("A->A|B"),
            parser.parse("A->B|A"),
            parser.parse("(A->B)->(C->B)->(A|C->B)"),
            parser.parse("(A->B)->(A->!B)->!A"),
            parser.parse("!!A->A")
    };


    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner(new File("input.txt"));
        PrintWriter out = new PrintWriter("output.txt");
        String s;
        int pos = 1;
        parseFirst(in.next());
        while ((s = in.next()) != null) {
            parseString(s, pos);
            pos++;
        }
        out.write(sb.toString());
        out.close();
    }

    private static void parseString(String s, int pos) {
        Expression obj = parser.parse(s);
        int he_obj = obj.hash();
        boolean fnd = false;
        if (assumptions.containsKey(he_obj)) {
            fnd = true;
            sb.append("(").append(pos).append(") ").append(s).append(" (Предп. ").append(assumptions.get(he_obj)).append(")\n");
        }
        if (!fnd) {
            int c = isAxiom(obj);
            if (c > 0) {
                fnd = true;
                sb.append("(").append(pos).append(") ").append(s).append(" (Сх. акс. ").append(c).append(")\n");
            }
        }
        if (!fnd) {
            if (forr.containsKey(he_obj)) {
                for (Expression e : forr.get(he_obj)) {
                    if (firstpos.containsKey(e.getLeft().hash())) {
                        fnd = true;
                        int bg = firstpos.get(e.hash());
                        int lt = firstpos.get(e.getLeft().hash());
                        sb.append("(").append(pos).append(") ").append(s).append(" (M.P. ").append(bg).append(", ").append(lt).append(")\n");
                        break;
                    }
                }
            }
        }
        if (!fnd) {
            sb.append("(").append(pos).append(") ").append(s).append(" (Не доказано)\n");
        }
        if (obj.getType() == 1) {
            int rgh = obj.getRight().hash();
            if (!forr.containsKey(rgh)) {
                forr.put(rgh, new ArrayList<>());
            }
            forr.get(rgh).add(obj);
        }
        firstpos.put(he_obj, pos);

    }

    private static void parseFirst(String s) {
        if (s.charAt(0) == '|') {
            return;
        }
        String c = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '|' && s.charAt(i + 1) == '-') {
                c = s.substring(0, i);
            }
        }
        if (c.isEmpty()) {
            return;
        }
        int num = 1;
        String[] v = c.split(",");
        for (String j : v) {
            if (j.length() > 0) {
                assumptions.put(parser.parse(j).hash(), num);
                num++;
            }
        }
    }


    private static Map<Integer, Integer> exist = new HashMap<>();

    private static int isAxiom(Expression e) {
        int num = 1;
        for (Expression s : e_axioms) {
            exist.clear();
            if (recAx(s, e)) {
                return num;
            }
            num++;
        }
        return -1;
    }

    private static boolean recAx(Expression ax, Expression my) {
        if (ax.getType() == 5) {
            int axe = ax.hash();
            int mye = my.hash();
            if (exist.containsKey(axe)) {
                return exist.get(axe).equals(mye);
            } else {
                exist.put(axe, mye);
                return true;
            }
        }
        if (ax.getType() == my.getType()) {
            if (ax.getType() == 4) {
                return recAx(ax.getLeft(), my.getLeft());
            }
            if (ax.getType() == my.getType()) {
                return recAx(ax.getRight(), my.getRight()) && recAx(ax.getLeft(), my.getLeft());
            }
        }
        return false;
    }
}
