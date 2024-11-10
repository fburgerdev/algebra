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
        // base
        if (subexpr instanceof Mul mul) {
            if (mul.left instanceof Log log) {
                return 1 + log.subexpr.countExprs() + mul.right.countExprs();
            }
        }
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
            if (num.value == 0.0) {
                return new Num(1.0);
            }
        }
        else if (simplified instanceof Log log) {
            return log.subexpr;
        }
        else if (simplified instanceof Mul mul) {
            if (mul.left instanceof Log log) {
                if (log.subexpr instanceof Num baseNum) {
                    if (mul.right instanceof Num expNum) {
                        return new Num(Math.pow(baseNum.value, expNum.value));
                    }
                }
            }
        }
        return new Exp(simplified);
    }
    // transform
    @Override
    public void transform(ExprCallback callback) {
        subexpr.transform(x -> callback.call(new Exp(x)));
    }

    // copy
    @Override
    public Expr copy() {
        return new Exp(subexpr.copy());
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

    // str
    @Override
    public String str() {
        // base
        if (subexpr instanceof Mul mul) {
            if (mul.left instanceof Log log) {
                String leftStr = log.subexpr.str();
                if (log.subexpr instanceof Add || log.subexpr instanceof Mul) {
                    leftStr = "(" + leftStr + ")";
                }
                String rightStr = mul.right.str();
                if (mul.right instanceof Add || mul.right instanceof Mul) {
                    rightStr = "(" + rightStr + ")";
                }
                return leftStr + "^" + rightStr;
            }
        }
        return "exp(" + subexpr.str() + ")";
    }
    // debugStr
    @Override
    public String debugStr() {
        return "EXP(" + subexpr.debugStr() + ")";
    }
}