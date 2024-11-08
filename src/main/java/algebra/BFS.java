package algebra;

import java.util.ArrayDeque;
import java.util.TreeSet;

public class BFS {
    // queue, visited
    public ArrayDeque<Expr> queue;
    public TreeSet<Expr> visited;

    public BFS(Expr initial) {
        queue = new ArrayDeque<>();
        queue.push(initial);
        visited = new TreeSet<>();
        visited.add(initial);
        while (!queue.isEmpty()) {
            step();
            System.out.println(visited.size());
        }
        for (Expr expr : visited) {
            System.out.println(expr.debugStr());
        }
    }

    public void step() {
        Expr top = queue.pop().copy();
        top.transform(() -> {
            Expr transformed = top.copy();
            if (!visited.contains(transformed)) {
                queue.push(transformed);
                visited.add(transformed);
            }
        });
    }
}