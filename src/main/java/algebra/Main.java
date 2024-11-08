package algebra;

public class Main {
    public static void main(String[] args) {
        Expr expr = new Add(
            new Add(
                new Var("x"),
                new Var("x")
            ),
            new Mul(
                new Log(
                    new Num(3.0)
                ),
                new Var("x")
            )
        );
        System.out.println(expr.debugStr());
        System.out.println("Complexity: " + expr.complexity());
        System.out.println("ExprCount: " + expr.countExprs());
        System.out.println("VarCount: " + expr.countVars());
        System.out.println("TransformCount: " + expr.countTransforms());
        System.out.println("Transforms:");
        expr.transform(x -> System.out.println(x.debugStr()));

        BFS bfs = new BFS(expr);
    }
}