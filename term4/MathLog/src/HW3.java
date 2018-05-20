import expression.*;
import parser.Deductor;
import parser.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class HW3 {

    private static HashSet<Expression> hypotheses = new HashSet<>();
    private static ArrayList<String> variables = new ArrayList<>();
    private static HashMap<String, Boolean> values = new HashMap<>();
    private static HashMap<String, ArrayList<String>> proofs = new HashMap<>();
    private static Parser parser = new Parser();

    private static String removeWhitespaces(String s) {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) ss.append(s.charAt(i));
        }
        return ss.toString();
    }

    private static void findVariables(Expression e) {
        if (e instanceof Variable) {
            if (!variables.contains (((Variable) e).getName())) {
                variables.add(((Variable) e).getName());
            }
        } else {
            Expression[] args = (e instanceof Negation) ? new Expression[]{e.getLeft()} : new Expression[]{e.getLeft(), e.getRight()};
            for (Expression ex : args) {
                findVariables(ex);
            }
        }
    }

    private static ArrayList<String> getProof(Expression ex) {
        ArrayList<String> out = new ArrayList<>();
        StringBuilder header = new StringBuilder();
        for (String var : variables) {
            header.append(values.get(var) ? "" : "!").append(var).append(",");
        }
        header.deleteCharAt(header.length() - 1);
        header.append("|-").append(toString(ex));
        out.add(header.toString());
        out.addAll(getChildrenProof(ex));
        return out;
    }

    private static ArrayList<String> getChildrenProof(Expression ex) {
        ArrayList<String> out = new ArrayList<>();
        if (ex instanceof Variable) {
            out.add((values.get(((Variable) ex).getName()) ? "" : "!") + ((Variable) ex).getName());
            return out;
        }
        Expression[] args = (ex instanceof Negation) ? new Expression[]{ex.getLeft()} : new Expression[]{ex.getLeft(), ex.getRight()};
        StringBuilder key = new StringBuilder(ex.getKey());
        for (Expression e : args) {
            out.addAll(getChildrenProof(e));
            key.append(e.evaluate(values) ? "1" : "0");
        }
        out.addAll(replace(proofs.get(key.toString()), args));
        return out;
    }

    private static ArrayList<String> replace(ArrayList<String> old, Expression[] sub) {
        ArrayList<String> out = new ArrayList<>();
        for (String s : old) {
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char cur = s.charAt(i);
                if (cur == 'A' || cur == 'B') {
                    res.append("(");
                    res.append(sub[cur - 'A']);
                    res.append(")");
                } else {
                    res.append(cur);
                }
            }
            out.add(res.toString());
        }
        return out;
    }

    private static String toString(Expression e){
        StringBuilder sb = new StringBuilder();
        e.write(sb);
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader in = new BufferedReader(new FileReader(new File("input.txt")));
             BufferedWriter out = new BufferedWriter(new FileWriter(new File("output.txt")))) {
            //Checker.check();
            //Thread.sleep(1000000);
            String s = removeWhitespaces(in.readLine());
            String[] h = s.split("\\|=")[0].split(",");
            Expression toProve = parser.parse(removeWhitespaces(s.split("\\|=")[1]));
            for (String aH : h) {
                if (!aH.equals("")) {
                    hypotheses.add(parser.parse(removeWhitespaces(aH)));
                }
            }
            for (Expression hypothese : hypotheses) {
                toProve = new Implication(hypothese, toProve);
            }

            findVariables(toProve);

            for (int i = 0; i < (1 << variables.size()); i++) {
                for (int j = 0; j < variables.size(); j++) {
                    values.put(variables.get(j), (i & (1 << j)) != 0);
                }
                if (!toProve.evaluate(values)) {
                    out.write("Высказывание ложно при ");
                    for (int q = 0; q < variables.size(); q++) {
                        String var = variables.get(q);
                        out.write(var + "=" + (values.get(var) ? "И" : "Л"));
                        if (q != variables.size() - 1) out.write(", ");
                    }
                    out.flush();
                    return;
                }
            }

            out.write(s.replace("|=", "|-") + "\n");

            BufferedReader p = new BufferedReader(new FileReader(new File("proofs.txt")));
            for (; p.readLine() != null; ) {
                String head = p.readLine();
                ArrayList<String> proof = new ArrayList<>();
                String line;
                while (!(line = p.readLine()).isEmpty()) {
                    proof.add(line);
                }
                proofs.put(head, proof);
            }

            for (int i = 0; i < (1 << variables.size()); i++) {
                for (int j = 0; j < variables.size(); j++) {
                    values.put(variables.get(j), (i & (1 << j)) != 0);
                }

                ArrayList<String> proof = getProof(toProve);
                for (int j = 0; j < variables.size(); j++) proof = Deductor.deduct(proof);
                proof.remove(0);
                for (String ss : proof) out.write(ss + "\n");

            }

            for (int i = 0; i < variables.size(); i++) {
                String var = variables.get(i);
                for (int j = 0; j < 1 << (variables.size() - i - 1); j++) {
                    StringBuilder formula = new StringBuilder();
                    for (int k = i + 1; k < variables.size(); k++) {
                        formula.append((j & (1 << (k - i - 1))) != 0 ? "" : "!").append(variables.get(k)).append("->");
                    }
                    formula.append("(").append(toProve).append(")");
                    ArrayList<String> AornotA = replace(proofs.get("AornotA"), new Expression[]{new Variable(var)});
                    ArrayList<String> eighth = replace(proofs.get("8th"), new Expression[]{new Variable(var), parser.parse(removeWhitespaces(formula.toString()))});
                    for (String ss : AornotA) out.write(ss + "\n");
                    for (String ss : eighth) out.write(ss + "\n");
                }
            }

            for (Expression e : hypotheses) out.write(toString(e) + "\n");
            for (int i = 0; i < hypotheses.size(); i++) {
                toProve = toProve.getRight();
                out.write(toString(toProve) + "\n");
            }
            out.flush();
        }
    }
}
