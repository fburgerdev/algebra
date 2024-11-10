package algebra;

public class Mul extends Expr {
    // left, right
    public Expr left, right;

    // constructor
    public Mul(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    // complexity
    @Override
    public int countExprs() {
        // division
        if (right instanceof Exp exp) {
            if (exp.subexpr instanceof Mul mul) {
                if (mul.left instanceof Log log && mul.right instanceof Num num) {
                    if (num.value == -1.0) {
                        return 1 + left.countExprs() + log.subexpr.countExprs();
                    }
                }
            }
        }

        return 1 + left.countExprs() + right.countExprs();
    }
    @Override
    public int countVars() {
        return left.countVars() + right.countVars();
    }
    // simplify
    @Override
    public Expr simplify() {
        Expr simpleLeft = left.simplify();
        Expr simpleRight = right.simplify();
        if (simpleLeft instanceof Num leftNum && simpleRight instanceof Num rightNum) {
            return new Num(leftNum.value * rightNum.value);
        }
        else if (simpleLeft instanceof Num leftNum) {
            if (leftNum.value == 0.0) {
                return new Num(0.0);
            }
            else if (leftNum.value == 1.0) {
                return simpleRight;
            }
        }
        else if (simpleRight instanceof Num rightNum) {
            if (rightNum.value == 0.0) {
                return new Num(0.0);
            }
            else if (rightNum.value == 1.0) {
                return simpleLeft;
            }
        }
        return new Mul(simpleLeft, simpleRight);
    }
    // transform
    @Override
    public void transform(ExprCallback callback) {
        // commute
        callback.call(new Mul(right, left));

        // associate
        if (left instanceof Mul leftMul) {
            if (right instanceof Mul rightMul) {
                callback.call(new Mul(new Mul(leftMul.left, rightMul.left), new Mul(leftMul.right, rightMul.right)));
                callback.call(new Mul(new Mul(leftMul.left, rightMul.right), new Mul(rightMul.left, leftMul.right)));
                callback.call(new Mul(new Mul(rightMul.left, leftMul.right), new Mul(leftMul.left, rightMul.right)));
                callback.call(new Mul(new Mul(rightMul.right, leftMul.right), new Mul(rightMul.left, leftMul.left)));
            }
            else {
                callback.call(new Mul(new Mul(leftMul.left, right), leftMul.right));
                callback.call(new Mul(new Mul(right, leftMul.right), leftMul.left));
            }
        }

        // promote
        if (left.compareTo(right) == 0) {
            callback.call(new Exp(new Mul(new Log(right), new Num(2.0))));
        }
        else if (left instanceof Exp leftExp && leftExp.subexpr instanceof Mul leftMul && leftMul.left instanceof Log leftLog) {
            if (leftLog.subexpr.compareTo(right) == 0) {
                callback.call(new Exp(new Mul(leftLog, new Mul(leftMul.right, new Num(1.0)))));
            }
            else if (right instanceof Exp rightExp && rightExp.subexpr instanceof Mul rightMul && rightMul.left instanceof Log rightLog) {
                if (leftLog.subexpr.compareTo(rightLog.subexpr) == 0) {
                    callback.call(new Exp(new Mul(leftLog, new Mul(leftMul.right, rightMul.right))));
                }
            }
        }

        // children
        left.transform(leftExpr -> callback.call(new Mul(leftExpr, right)));
        right.transform(rightExpr -> callback.call(new Mul(left, rightExpr)));
    }

    // copy
    @Override
    public Expr copy() {
        return new Mul(left.copy(), right.copy());
    }
    // compareTo
    @Override
    public int compareTo(Expr other) {
        if (other instanceof Mul otherMul) {
            int leftCompare = left.compareTo(otherMul.left);
            if (leftCompare == 0) {
                return right.compareTo(otherMul.right);
            }
            else {
                return leftCompare;
            }
        }
        else {
            return compareSubtypes(other);
        }
    }

    // str
    @Override
    public String str() {
        // division
        if (right instanceof Exp exp) {
            if (exp.subexpr instanceof Mul mul) {
                if (mul.left instanceof Log log && mul.right instanceof Num num) {
                    if (num.value == -1.0) {
                        String leftStr = left.str();
                        if (left instanceof Add) {
                            leftStr = "(" + leftStr + ")";
                        }
                        String rightStr = log.subexpr.str();
                        if (log.subexpr instanceof Add) {
                            rightStr = "(" + rightStr + ")";
                        }
                        return leftStr + "/" + rightStr;
                    }
                }
            }
        }
        String leftStr = left.str();
        if (left instanceof Add) {
            leftStr = "(" + leftStr + ")";
        }
        String rightStr = right.str();
        if (right instanceof Add) {
            rightStr = "(" + rightStr + ")";
        }
        return leftStr + "*" + rightStr;
    }
    // debugStr
    @Override
    public String debugStr() {
        return "MUL(" + left.debugStr() + "," + right.debugStr() + ")";
    }
}