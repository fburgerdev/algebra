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

    // transform
    @Override
    public void transform(Callback callback) {
        // pass
    }

    // copy
    @Override
    public Expr copy() {
        return new Num(value);
    }
    // equals
    @Override
    public boolean equals(Expr other) {
        if (other instanceof Num otherNum) {
            return value == otherNum.value;
        }
        return false;
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

    // debug
    @Override
    public String debugStr() {
        return String.valueOf(value);
    }
}