package parser;

import expression.Expression;
import expression.Implication;
import expression.Variable;

import java.util.*;


public class Deductor {

    private static Parser parser = new Parser();

    private static String removeWhitespaces(String s) {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) ss.append(s.charAt(i));
        }
        return ss.toString();
    }

    private static Expression[] axioms = new Expression[]{
            parser.parse("A->B->A"),
            parser.parse("(A->B)->(A->B->C)->(A->C)"),
            parser.parse("A->B->A&B"),
            parser.parse("(A&B)->A"),
            parser.parse("(A&B)->B"),
            parser.parse("A->(A|B)"),
            parser.parse("B->(A|B)"),
            parser.parse("(A->C)->(B->C)->((A|B)->C)"),
            parser.parse("(A->B)->(A->(!B))->(!A)"),
            parser.parse("(!!A)->A")
    };

    private static int check(HashMap<String, Expression> sub, Expression ex) {
        for (int i = 0; i < 10; i++) {
            sub.clear();
            if (checkAxiom(sub, axioms[i], ex)) return i + 1;
        }
        return 0;
    }

    private static boolean checkAxiom(HashMap<String, Expression> sub, Expression ax, Expression ex) {
        if (ax instanceof Variable) {
            Variable a = (Variable) ax;
            if (sub.containsKey(a.getName())) {
                return sub.get(a.getName()).equals(ex);
            } else {
                sub.put(a.getName(), ex);
                return true;
            }
        } else if (ax.getKey().equals(ex.getKey())) {
            return checkAxiom(sub, ax.getLeft(), ex.getLeft()) && checkAxiom(sub, ax.getRight(), ex.getRight());
        }
        return false;

    }

    private static void writeHypotheseOrAxiom(Expression a, Expression e, ArrayList<String> w) {
        String ee = e.toString();
        w.add("(" + ee + "->(" + a.toString() + "->" + ee + "))");
        w.add(ee);
    }

    private static void writeAToA(Expression a, ArrayList<String> w) {
        String s = "(" + a.toString() + ")";
        w.add("(" + s + "->(" + s + "->" + s + "))");
        w.add("(" + s + "->((" + s + "->" + s + ")->" + s + "))");
        w.add("((" + s + "->(" + s + "->" + s + "))->((" + s + "->((" + s + "->" + s + ")->" + s + "))->(" + s + "->" + s + ")))");
        w.add("((" + s + "->((" + s + "->" + s + ")->" + s + "))->(" + s + "->" + s + "))");
    }

    private static void writeMP(Expression left, Expression right, Expression alpha, ArrayList<String> w) {
        String l = "(" + left.toString() + ")", r = "(" + right.toString() + ")", a = "(" + alpha.toString() + ")";
        w.add("(" + a + "->" + l + ")->((" + a + "->(" + l + "->" + r + "))->(" + a + "->" + r + "))");
        w.add("((" + a + "->(" + l + "->" + r + "))->(" + a + "->" + r + "))");
    }

    public static ArrayList<String> deduct(ArrayList<String> proof) {
        HashSet<Expression> hypotheses = new HashSet<>();
        HashSet<Expression> proved = new HashSet<>();
        HashMap<Expression, Expression> deduct = new HashMap<>();
        HashMap<Expression, List<Expression>> toDeduct = new HashMap<>();
        HashMap<String, Expression> sub = new HashMap<>();
        ArrayList<String> out = new ArrayList<>();
        Iterator<String> it = proof.iterator();
        String s = it.next();
        String[] h = s.split("\\|-")[0].split(",");
        String toProve = parser.parse(s.split("\\|-")[1]).toString();
        StringBuilder header = new StringBuilder();
        for (int i = 0; i < h.length - 1; i++) {
            header.append(parser.parse(h[i]).toString());
            if (i != h.length - 2) header.append(",");
            if (!h[i].equals("")) hypotheses.add(parser.parse(h[i]));
        }
        Expression alpha = parser.parse(h[h.length - 1]);
        header.append("|-(").append(parser.parse(h[h.length - 1]).toString()).append(")->(").append(parser.parse(toProve).toString()).append(")");
        out.add(header.toString());
        while (it.hasNext()) {
            s = it.next();
            s = removeWhitespaces(s);
            if (s.isEmpty()) {
                continue;
            }
            Expression ex = parser.parse(s);
            if (hypotheses.contains(ex) || check(sub, ex) != 0) {
                writeHypotheseOrAxiom(alpha, ex, out);
            } else if (ex.equals(alpha)) {
                writeAToA(ex, out);
            } else {
                Expression left = deduct.get(ex).getLeft();
                writeMP(left, ex, alpha, out);
            }
            proved.add(ex);
            out.add("(" + alpha.toString() + ")->(" + ex.toString() + ")");
            if (ex instanceof Implication) {
                Implication im = (Implication) ex;
                if (proved.contains(im.getLeft())) {
                    deduct.put(im.getRight(), ex);
                } else {
                    toDeduct.computeIfAbsent(im.getLeft(), k -> new ArrayList<>());
                    toDeduct.get(im.getLeft()).add(im.getRight());
                }
            }

            if (toDeduct.containsKey(ex)) {
                for (Expression e : toDeduct.get(ex)) {
                    deduct.put(e, parser.parse("(" + ex.toString() + ")->(" + e.toString() + ")"));
                }
            }
        }
        return out;
    }

}
