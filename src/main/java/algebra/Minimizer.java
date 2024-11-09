package algebra;

import java.util.ArrayDeque;
import java.util.TreeSet;

public class Minimizer {
    // queue, visited
    public ArrayDeque<Expr> queue;
    public TreeSet<Expr> visited;

    // constructor
    public Minimizer(Expr initial) {
        Expr simplified = initial.simplify();
        queue = new ArrayDeque<>();
        queue.push(simplified);
        visited = new TreeSet<>();
        visited.add(simplified);
        while (!queue.isEmpty()) {
            step();
        }
        // System.out.println("visited: " + visited.size());
    }

    // step
    public final void step() {
        Expr top = queue.pop().copy();
        top.transform(transformed -> {
            if (!visited.contains(transformed)) {
                Expr simplified = transformed.simplify();
                queue.push(simplified);
                visited.add(simplified);
            }
        });
    }

    // findMinimum
    public final Expr findMinimum() {
        Expr minExpr = null;
        int minEval = 9999;
        for (Expr expr : visited) {
            int eval = expr.complexity();
            if (eval < minEval) {
                minExpr = expr;
                minEval = eval;
            }
        }
        return minExpr;
    }
}