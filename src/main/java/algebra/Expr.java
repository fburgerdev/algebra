package algebra;

public interface Expr {
    // complexity
    public double complexity();
    // simplify
    public Expr simplify();
    // floatLeft
    public void floatLeft();

    // debugStr
    public String debugStr();
}