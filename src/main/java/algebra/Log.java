package algebra;

public class Log extends Expr {
    // subexpr
    public Expr subexpr;

    // constructor
    public Log(Expr subexpr) {
        this.subexpr = subexpr;
    }

    // complexity
    @Override
    public int countExprs() {
        return 1 + subexpr.countExprs();
    }
    @Override
    public int countVars() {
        return subexpr.countVars();
    }

    // transform
    @Override
    public void transform(Callback callback) {
        subexpr.transform(callback);
    }

    // copy
    @Override
    public Expr copy() {
        return new Log(subexpr.copy());
    }
    // equals
    @Override
    public boolean equals(Expr other) {
        if (other instanceof Log otherLog) {
            return subexpr.equals(otherLog.subexpr);
        }
        return false;
    }
    // compareTo
    @Override
    public int compareTo(Expr other) {
        if (other instanceof Log otherLog) {
            return subexpr.compareTo(otherLog.subexpr);
        }
        else {
            return compareSubtypes(other);
        }
    }

    // debug
    @Override
    public String debugStr() {
        return "LOG(" + subexpr.debugStr() + ")";
    }
}