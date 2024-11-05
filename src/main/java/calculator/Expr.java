package calculator;

public interface Expr {
    // simplify
    public Expr simplify();
    // floatLeft
    public void floatLeft();
    // debugStr
    public String debugStr();
}