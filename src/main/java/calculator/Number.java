package calculator;

public class Number implements Expr {
    // value
    public double value;

    // constructor
    public Number(double value) {
        this.value = value;
    }
    // simplify
    @Override
    public Expr simplify() {
        return this;
    }
    // floatLeft
    @Override
    public void floatLeft() {
        // pass
    }
    
    // debugStr
    @Override
    public String debugStr() {
        return "NUM " + String.valueOf(value);
    }
}