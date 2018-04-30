package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static class Thing {
        int x1, x2, y1, y2;

        Thing(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    private static int dist(Thing first, Thing second) {
        int dx = 0, dy = 0;

        if (first.x1 > second.x2) {
            dx = first.x1 - second.x2;
        }

        if (second.x1 > first.x2) {
            dx = second.x1 - first.x2;
        }

        if (first.y1 > second.y2) {
            dy = first.y1 - second.y2;
        }

        if (second.y1 > first.y2) {
            dy = second.y1 - first.y2;
        }
        /*if (first.x1 <= second.x1 && second.x1 <= first.x2 ||
                first.x1 <= second.x2 && second.x2 <= first.x2 ||
                second.x1 <= first.x1 && first.x1 <= second.x2 ||
                second.x1 <= first.x2 && first.x2 <= second.x2) {
            dx = 0;
        } else {
            dx = Math.min(Math.abs(first.x1 - second.x2), Math.abs(first.x1 - second.x2));
        }

        if (first.y1 <= second.y1 && second.y1 <= first.y2 ||
                first.y1 <= second.y2 && second.y2 <= first.y2 ||
                second.y1 <= first.y1 && first.y1 <= second.y2 ||
                second.y1 <= first.y2 && first.y2 <= second.y2) {
            dy = 0;
        } else {
            dy = Math.min(Math.abs(first.y1 - second.y2), Math.abs(first.y1 - second.y2));
        }*/

        return Math.max(dx, dy);
    }

    private static int nextInt(BufferedReader in) throws IOException {
        int ans = 0;
        int ch = in.read();
        while (ch != '-' && (ch < '0' || ch > '9')) {
            ch = in.read();
        }
        boolean isNegate = false;
        if (ch == '-') {
            isNegate = true;
            ch = in.read();
        }
        while (ch >= '0' && ch <= '9') {
            ans = 10 * ans + ch - '0';
            ch = in.read();
        }
        return isNegate ? -ans : ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int n = nextInt(in);
        int w = nextInt(in);
        ArrayList<ArrayList<Pair<Integer, Integer>>> g = new ArrayList<>();

        for (int i = 0; i < n + 2; i++) {
            g.add(new ArrayList<>());
        }

        ArrayList<Thing> things = new ArrayList<>();
        things.add(new Thing(-Integer.MAX_VALUE, w, Integer.MAX_VALUE, w + 1));
        things.add(new Thing(-Integer.MAX_VALUE, -1, Integer.MAX_VALUE, 0));

        for (int i = 0; i < n; i++) {
            things.add(new Thing(nextInt(in), nextInt(in), nextInt(in), nextInt(in)));
        }

        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j <= i; j++) {
                if (i != j) {
                    g.get(i).add(new Pair<>(j, dist(things.get(i), things.get(j))));

                }
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n + 2; i++) {
            ans.add(Integer.MAX_VALUE);
        }

        ArrayList<Boolean> used = new ArrayList<>();
        for (int i = 0; i < n + 2; i++) {
            used.add(false);
        }

        ans.set(0, 0);

        for (int i = 0; i < n + 2; i++) {
            int temp = -1;

            for (int j = 0; j < n + 2; j++) {
                if (!used.get(j) && (temp == -1 || ans.get(j) < ans.get(temp))) {
                    temp = j;
                }
            }

            if (ans.get(temp) != Integer.MAX_VALUE) {
                for (Pair<Integer, Integer> cur : g.get(temp)) {
                    ans.set(cur.getKey(), Math.min(ans.get(cur.getKey()), ans.get(temp) + cur.getValue()));
                }
                used.set(temp, true);
            } else {
                break;
            }
        }

        out.println(ans.get(1));
        out.close();
    }
}
