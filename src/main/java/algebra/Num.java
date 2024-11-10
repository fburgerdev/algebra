package algebra;

public class Num extends Expr {
    // value
    public double value;

    // constructor
    public Num(double value) {
        this.value = value;
    }

    // complexity
    @Override
    public int countExprs() {
        return 1;
    }
    @Override
    public int countVars() {
        return 0;
    }
    // simplify
    @Override
    public Expr simplify() {
        return new Num(value);
    }
    // transform
    @Override
    public void transform(ExprCallback callback) {
        // pass
    }

    // copy
    @Override
    public Expr copy() {
        return new Num(value);
    }
    // compareTo
    @Override
    public int compareTo(Expr other) {
        if (other instanceof Num otherNum) {
            return Double.compare(value, otherNum.value);
        }
        else {
            return compareSubtypes(other);
        }
    }

    // str
    @Override
    public String str() {
        if (value % 1.0 == 0) {
            return String.format("%.0f", value);  
        }
        else {
            return String.format("%s", value);
        }
    }
    // debugStr
    @Override
    public String debugStr() {
        return String.valueOf(value);
    }
}