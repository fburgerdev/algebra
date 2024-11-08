package algebra;

public class Fraction implements Expr {
    // upper, lower
    public Expr upper;
    public Expr lower;
    
    // constructor
    public Fraction(Expr upper, Expr lower) {
        this.upper = upper;
        this.lower = lower;
    }
    // eval
    public Number eval(Number upper, Number lower) {
        return new Number(upper.value / lower.value);
    }

    // complexity
    @Override
    public double complexity() {
        return 1.0 + upper.complexity() + lower.complexity();
    }
    // simplify
    @Override
    public Expr simplify() {
        upper = upper.simplify();
        lower = lower.simplify();
        if (upper instanceof Number upperNum && lower instanceof Number lowerNum) {
            return new Number(upperNum.value / lowerNum.value);
        }
        return this;
    }
    // floatLeft
    @Override
    public void floatLeft() {

    }

    // debugStr
    @Override
    public String debugStr() {
        String upperString = upper.debugStr().replaceAll("\n", "\n  ");
        String lowerString = lower.debugStr().replaceAll("\n", "\n  ");
        return "FRC\n  " + upperString + "\n  " + lowerString;
    }
}