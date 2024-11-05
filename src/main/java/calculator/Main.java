package calculator;

public class Main {
    public static void main(String[] args) {
        Expr expr = Parser.parse("(1 + x) + (2 + x) * 2 + (3 + x) + (4 + x)");
        System.out.println(expr.debugStr());
        for (int i = 0; i < 5; ++i) {
            System.out.println("\nIteration" + i);
            expr.floatLeft();
            System.out.println(expr.debugStr());
            expr = expr.simplify();
            System.out.println(expr.debugStr());
        }
    }
}