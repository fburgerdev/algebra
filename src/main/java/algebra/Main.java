package algebra;

public class Main {
    public static void main(String[] args) {
        Expr parsed = Parser.parse("x + (2 * y / 2)");
        System.out.println(parsed.str());
        
        Expr expr = new Add(
            new Add(
                new Add(
                    new Add(
                        new Var("x"),
                        new Num(1.0)
                    ),
                    new Var("x")
                ),
                new Num(1.0)
            ),
            new Var("x")
        );
        expr = parsed;
        System.out.println(expr.debugStr());
        System.out.println("Complexity: " + expr.complexity());
        System.out.println("ExprCount: " + expr.countExprs());
        System.out.println("VarCount: " + expr.countVars());
        System.out.println("TransformCount: " + expr.countTransforms());
        System.out.println("Transforms:");
        
        Expr minimum = new Minimizer(parsed).findMinimum();
        System.out.println("Minimum: " + minimum.str());
        System.out.println(minimum.debugStr());
    }
}