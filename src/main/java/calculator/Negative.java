package calculator;

public class Negative implements Expr {
    // subexpr
    public Expr subexpr;

    // constructor
    public Negative(Expr subexpr) {
        this.subexpr = subexpr;
    }
    // eval
    public Number eval(Number subexpr) {
        return new Number(-subexpr.value);
    }
    // simplify
    @Override
    public Expr simplify() {
        subexpr = subexpr.simplify();
        if (subexpr instanceof Number number) {
            return new Number(-number.value);
        }
        else if (subexpr instanceof Negative negative) {
            return negative.subexpr;
        }
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
        String subString = subexpr.debugStr().replaceAll("\n", "\n  ");
        return "NEG\n  " + subString;
    }
}