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
    // simplify
    @Override
    public Expr simplify() {
        Expr simplified = subexpr.simplify();
        if (simplified instanceof Num num) {
            if (num.value == 1.0) {
                return new Num(0.0);
            }
        }
        else if (simplified instanceof Exp exp) {
            return exp.subexpr;
        }
        return new Log(simplified);
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

    // str
    @Override
    public String str() {
        return "log(" + subexpr.str() + ")";
    }
    // debugStr
    @Override
    public String debugStr() {
        return "LOG(" + subexpr.debugStr() + ")";
    }
}