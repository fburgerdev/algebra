package algebra;

public class Exp extends Expr {
    // subexpr
    public Expr subexpr;

    // constructor
    public Exp(Expr subexpr) {
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
        subexpr.transform(x -> callback.call(new Exp(x)));
    }

    // copy
    @Override
    public Expr copy() {
        return new Log(subexpr.copy());
    }
    // compareTo
    @Override
    public int compareTo(Expr other) {
        if (other instanceof Exp otherExp) {
            return subexpr.compareTo(otherExp.subexpr);
        }
        else {
            return compareSubtypes(other);
        }
    }

    // debug
    @Override
    public String debugStr() {
        return "EXP(" + subexpr.debugStr() + ")";
    }
}