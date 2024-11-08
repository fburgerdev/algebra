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
    public void transform(ExprCallback callback) {
        subexpr.transform(x -> callback.call(new Log(x)));
    }

    // copy
    @Override
    public Expr copy() {
        return new Log(subexpr.copy());
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