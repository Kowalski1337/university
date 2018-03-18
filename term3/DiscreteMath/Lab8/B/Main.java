import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int i, j, k, n, vertex, edge;

    public static ArrayList<Integer> ext_path(ArrayList<ArrayList<Integer>> G, ArrayList<Integer> path) {
        int i, j, k;
        int n = G.size();
        ArrayList<Integer> extended_path = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        for (i = 0; i < n; i++) {
            visited.add(0);
        }
        int present = 0;
        for (i = 0; i < path.size(); i++) {
            present = path.get(i);
            visited.set(present, 1);
            extended_path.add(present);
        }
        for (k = 0; k < n; k++) {
            ArrayList<Integer> neigh = new ArrayList<>();
            for (i = 0; i < n; i++)
                if (G.get(present).get(i) == 1 && visited.get(i) == 0)
                    neigh.add(i);
            if (!neigh.isEmpty()) {
                int choice = neigh.get(0);
                int minimum = n;
                for (i = 0; i < neigh.size(); i++) {
                    ArrayList<Integer> next_neigh = new ArrayList<>();
                    for (j = 0; j < n; j++)
                        if (G.get(neigh.get(i)).get(j) == 1 && visited.get(j) == 0) {
                            next_neigh.add(j);
                        }
                    int eta = next_neigh.size();
                    if (eta < minimum) {
                        choice = neigh.get(i);
                        minimum = eta;
                    }
                }
                present = choice;
                visited.set(present, 1);
                extended_path.add(present);
            } else {
                break;
            }
        }
        return extended_path;
    }

    public static ArrayList<Integer> func_neigh(ArrayList<ArrayList<Integer>> G, ArrayList<Integer> path) {
        int i, j, k, n = G.size();
        boolean quit = false;
        while (quit != true) {
            int m = path.size(), inlet = -1, outlet = -1;
            ArrayList<Integer> neigh = new ArrayList<>();
            for (i = 0; i < path.size(); i++)
                if (G.get(path.get(m - 1)).get(path.get(i)) == 1) neigh.add(i);
            ArrayList<Integer> unvisited = new ArrayList<>();
            for (i = 0; i < n; i++) {
                boolean outside = true;
                for (j = 0; j < path.size(); j++)
                    if (i == path.get(j)) outside = false;
                if (outside == true) unvisited.add(i);
            }
            if ((!unvisited.isEmpty()) && (!neigh.isEmpty())) {
                int maximum = 0;
                for (i = 0; i < neigh.size(); i++)
                    for (j = 0; j < unvisited.size(); j++)
                        if (G.get(path.get(neigh.get(i) + 1)).get(unvisited.get(j)) == 1) {
                            ArrayList<Integer> next_neigh = new ArrayList<>();
                            for (k = 0; k < unvisited.size(); k++)
                                if (G.get(unvisited.get(j)).get(unvisited.get(k)) == 1)
                                    next_neigh.add(unvisited.get(k));
                            int eta = next_neigh.size();
                            if (eta >= maximum) {
                                inlet = neigh.get(i);
                                outlet = unvisited.get(j);
                                maximum = eta;
                            }
                        }
            }
            ArrayList<Integer> extended_path = new ArrayList<>();
            if (inlet != -1 && outlet != -1) {
                for (i = 0; i <= inlet; i++)
                    extended_path.add(path.get(i));
                for (i = path.size() - 1; i > inlet; i--)
                    extended_path.add(path.get(i));
                extended_path.add(outlet);
            }
            if (!extended_path.isEmpty()) path = extended_path;
            if (m < path.size()) path = ext_path(G, path);
            else quit = true;
        }
        return path;
    }

    public static ArrayList<Integer> temp_G(ArrayList<ArrayList<Integer>> G, ArrayList<Integer> path) {
        int i, j, k, l, p, n = G.size();
        boolean quit = false;
        while (quit != true) {
            ArrayList<Integer> extended_path = new ArrayList<>();
            int m = path.size();
            ArrayList<Integer> unvisited = new ArrayList<>();
            for (i = 0; i < n; i++) {
                boolean outside = true;
                for (j = 0; j < path.size(); j++)
                    if (i == path.get(j)) outside = false;
                if (outside == true) unvisited.add(i);
            }
            boolean big_check = false;
            for (i = 0; i < path.size(); i++) {
                for (j = 0; j < unvisited.size(); j++) {
                    if (G.get(unvisited.get(j)).get(path.get(i)) == 1) {
                        ArrayList<Integer> temp_path = new ArrayList<>();
                        temp_path.add(unvisited.get(j));
                        ArrayList<Integer> temp_extended_path = new ArrayList<>();
                        ArrayList<Integer> temp_visited = new ArrayList<>();
                        for (l = 0; l < n; l++)
                            temp_visited.add(0);
                        int present = 0;
                        for (l = 0; l < temp_path.size(); l++) {
                            present = temp_path.get(l);
                            temp_visited.set(present, 1);
                            temp_extended_path.add(present);
                        }
                        for (l = 0; l < n; l++) {
                            boolean unfound = true;
                            for (k = 0; k < unvisited.size(); k++)
                                if (l == unvisited.get(k)) unfound = false;
                            if (unfound == true) temp_visited.set(l, 1);
                        }
                        for (l = 0; l < n; l++) {
                            ArrayList<Integer> neigh = new ArrayList<>();
                            for (l = 0; l < n; l++)
                                if (G.get(present).get(l) == 1 && temp_visited.get(l) == 0)
                                    neigh.add(l);
                            if (!neigh.isEmpty()) {
                                int choice = neigh.get(0);
                                int minimum = n;
                                for (l = 0; l < neigh.size(); l++) {
                                    ArrayList<Integer> next_neigh = new ArrayList<>();
                                    for (k = 0; k < n; k++)
                                        if (G.get(neigh.get(l)).get(k) == 1 && temp_visited.get(k) == 0)
                                            next_neigh.add(k);
                                    int eta = next_neigh.size();
                                    if (eta < minimum) {
                                        choice = neigh.get(l);
                                        minimum = eta;
                                    }
                                }
                                present = choice;
                                temp_visited.set(present, 1);
                                temp_extended_path.add(present);
                            } else break;
                        }
                        int last_vertex = temp_extended_path.get(temp_extended_path.size() - 1);
                        int vj = 0;
                        boolean check = false;
                        while (check == false && !temp_extended_path.isEmpty()) {
                            for (p = path.size() - 2; p > i; p--) {
                                if (G.get(path.get(p)).get(last_vertex) == 1
                                        && G.get(path.get(i + 1)).get(path.get(p + 1)) == 1) {
                                    check = true;
                                    vj = p;
                                    break;
                                }
                            }
                            if (check == false) {
                                temp_extended_path.remove(temp_extended_path.size() - 1);
                                last_vertex = temp_extended_path.get(temp_extended_path.size() - 1);
                            }
                        }
                        if (check == true) {
                            ArrayList<Integer> temp = new ArrayList<>();
                            for (p = 0; p <= i; p++)
                                temp.add(path.get(p));
                            for (p = 0; p < temp_extended_path.size(); p++)
                                temp.add(temp_extended_path.get(p));
                            for (p = vj; p > i; p--)
                                temp.add(path.get(p));
                            for (p = vj + 1; p < path.size(); p++)
                                temp.add(path.get(p));
                            temp_extended_path = temp;
                            big_check = true;
                            extended_path = temp_extended_path;
                        }
                    }
                }
                if (big_check == true) {
                    break;
                }
            }
            if (!extended_path.isEmpty()) path = extended_path;
            if (m < path.size()) {
                path = ext_path(G, path);
                path = func_neigh(G, path);
            } else quit = true;
        }
        return path;
    }

    public static ArrayList<Integer> r_path(ArrayList<ArrayList<Integer>> G, ArrayList<Integer> path) {
        ArrayList<Integer> reversed_path = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) reversed_path.add(path.get(i));
        reversed_path = temp_G(G, reversed_path);
        return reversed_path;
    }

    public static ArrayList<Integer> cycle(ArrayList<ArrayList<Integer>> G, ArrayList<Integer> path) {

        int i, n = path.size();

        ArrayList<Integer> circuit_maker = new ArrayList<>();
        for (i = 0; i < n - 1; i++)
            if ((G.get(path.get(0)).get(path.get(i + 1)) == 1) && (G.get(path.get(i)).get(path.get(n - 1)) == 1))
                circuit_maker.add(i);
        return circuit_maker;
    }

    public static ArrayList<Integer> sort(ArrayList<ArrayList<Integer>> G) {
        int i, j;
        ArrayList<Integer> degree = new ArrayList<>();
        //System.out.println("G: " + G.size());
        for (i = 0; i < G.size(); i++) {
            int sum = 0;
            for (j = 0; j < G.get(i).size(); j++)
                if (G.get(i).get(j) == 1) sum++;
            degree.add(sum);
        }
        ArrayList<Integer> index = new ArrayList<>();
        for (i = 0; i < degree.size(); i++) index.add(i);
        //System.out.println("degree: " + degree.size());
        for (i = 0; i < degree.size(); i++)
            for (j = i + 1; j < degree.size(); j++)
                if (degree.get(i) < degree.get(j)) {
                    int a = index.get(i);
                    index.set(i, index.get(j));
                    index.set(j, a);
                }
        return index;
    }

    public static ArrayList<ArrayList<Integer>> reindex(ArrayList<ArrayList<Integer>> G, ArrayList<Integer> index) {

        ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
        for (int i = 0; i < G.size(); i++){
            ArrayList<Integer> x = new ArrayList<>();
            for (int j = 0 ; j< G.get(i).size(); j++){
                x.add(G.get(i).get(j));
            }
            temp.add(x);
        }
        //System.out.println("G: " + G.size());
//        for (int i = 0; i < G.size(); i++) {
//            for (int j = 0; j < G.get(i).size(); j++) {
//                System.out.print(G.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.get(i).size(); j++) {
                temp.get(i).set(j, G.get(index.get(i)).get(index.get(j)));
           //     System.out.println(index.get(i) + " " + index.get(j) + " " + G.get(index.get(i)).get(index.get(j)));
            }
        }
        return temp;
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("chvatal.in"));
        PrintWriter out = new PrintWriter("chvatal.out");

        n = in.nextInt();
        //System.out.println("n: " + n);
        int ms[][] = new int[n][n];
        for (int i = 0; i < n; i++)
            ms[i][i] = 0;
        String s = in.nextLine();
        for (int i = 0; i < n; i++) {
            s = in.nextLine();
            //System.out.println("lol" + s);
            for (int j = 0; j < i; j++) {
                char c = s.charAt(j);
                ms[i][j] = c - '0';
                ms[j][i] = c - '0';
            }
        }
        ArrayList<ArrayList<Integer>> G = new ArrayList<>();

        //System.out.println("G: " + G.size());
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                edge = ms[i][j];
                row.add(edge);
            }
            G.add(row);
        }
        ArrayList<Integer> index = sort(G);

//        for (int i = 0; i < index.size(); i++){
//            System.out.print(index.get(i) + " ");
//        }
//        System.out.println();
//
//        System.out.println("G: " + G.size());
//        for (int i = 0; i < G.size(); i++) {
//            for (int j = 0; j < G.get(i).size(); j++) {
//                System.out.print(G.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }
        G = reindex(G, index);
//        System.out.println("G: " + G.size());
//        for (int i = 0; i < G.size(); i++) {
//            for (int j = 0; j < G.get(i).size(); j++) {
//                System.out.print(G.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }

        for (vertex = 0; vertex < n; vertex++) {
            ArrayList<Integer> path = new ArrayList<>();
            path.add(vertex);
            path = ext_path(G, path);
            path = func_neigh(G, path);
            k = path.size();
            if (k < n) {
                path = temp_G(G, path);
//                for (int i = 0; i < path.size(); i++){
//                    System.out.print(path.get(i) + " ");
//                }
//                System.out.println();
                k = path.size();
            }
            if (k < n) {
                path = r_path(G, path);
//                for (int i = 0; i < path.size(); i++){
//                    System.out.print(path.get(i) + " ");
//                }
//                System.out.println();
                k = path.size();
            }

            if (k == n) {
                ArrayList<Integer> otv = new ArrayList<>();
                ArrayList<Integer> circuit_maker = cycle(G, path);
//                for (int i = 0; i < path.size(); i++){
//                    System.out.print(path.get(i) + " ");
//                }
//                System.out.println();
//                for (int i = 0; i < circuit_maker.size(); i++){
//                    System.out.print(circuit_maker.get(i) + " ");
//                }
//                System.out.println();
                if (!circuit_maker.isEmpty()) {
                    for (j = 0; j < circuit_maker.size(); j++) {
//                                     System.out.println("cm: " + circuit_maker.size());
                        for (k = 0; k <= circuit_maker.get(j); k++)
                            out.print((index.get(path.get(k)) + 1) + " ");
                        for (k = n - 1; k > circuit_maker.get(j); k--)
                            out.print((index.get(path.get(k)) + 1) + " ");
                        out.println();
                        break;
                    }
                }
                out.println();
            }
            break;
        }
        out.close();
        return;
    }
}
