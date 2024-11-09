package algebra;

public class Add extends Expr {
    // left, right
    public Expr left, right;

    // constructor
    public Add(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    // complexity
    @Override
    public int countExprs() {
        // subtraction
        if (right instanceof Mul mul) {
            if (mul.left instanceof Num num) {
                if (num.value == -1.0) {
                    return 1 + left.countExprs() + mul.right.countExprs();
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
            return new Num(leftNum.value + rightNum.value);
        }
        else if (simpleLeft instanceof Num leftNum && leftNum.value == 0.0) {
            return simpleRight;
        }
        else if (simpleRight instanceof Num rightNum && rightNum.value == 0.0) {
            return simpleLeft;
        }
        return new Add(simpleLeft, simpleRight);
    }

    // transform
    @Override
    public void transform(ExprCallback callback) {
        // commute
        callback.call(new Add(right, left));

        // associate
        if (left instanceof Add leftAdd) {
            if (right instanceof Add rightAdd) {
                callback.call(new Add(new Add(leftAdd.left, rightAdd.left), new Add(leftAdd.right, rightAdd.right)));
                callback.call(new Add(new Add(leftAdd.left, rightAdd.right), new Add(rightAdd.left, leftAdd.right)));
                callback.call(new Add(new Add(rightAdd.left, leftAdd.right), new Add(leftAdd.left, rightAdd.right)));
                callback.call(new Add(new Add(rightAdd.right, leftAdd.right), new Add(rightAdd.left, leftAdd.left)));
            }
            else {
                callback.call(new Add(new Add(leftAdd.left, right), leftAdd.right));
                callback.call(new Add(new Add(right, leftAdd.right), leftAdd.left));
            }
        }
        else if (right instanceof Add rightAdd){
            callback.call(new Add(rightAdd.right, new Add(rightAdd.left, left)));
            callback.call(new Add(rightAdd.left, new Add(left, rightAdd.right)));
        }

        // promote
        if (left.compareTo(right) == 0) {
            callback.call(new Mul(new Num(2.0), right));
        }
        else if (left instanceof Mul leftMul) {
            if (leftMul.left.compareTo(right) == 0) {
                callback.call(new Mul(new Add(leftMul.right, new Num(1.0)), right));
            }
            else if (leftMul.right.compareTo(right) == 0) {
                callback.call(new Mul(new Add(leftMul.left, new Num(1.0)), right));
            }
            else if (right instanceof Mul rightMul) {
                if (leftMul.left.compareTo(rightMul.left) == 0) {
                    callback.call(new Mul(new Add(leftMul.right, rightMul.right), leftMul.left));
                }
                else if (leftMul.left.compareTo(rightMul.right) == 0) {
                    callback.call(new Mul(new Add(leftMul.right, rightMul.left), leftMul.left));
                }
                else if (leftMul.right.compareTo(rightMul.left) == 0) {
                    callback.call(new Mul(new Add(leftMul.left, rightMul.right), leftMul.right));
                }
                else if (leftMul.right.compareTo(rightMul.right) == 0) {
                    callback.call(new Mul(new Add(leftMul.left, rightMul.left), leftMul.right));
                }
            }
        }

        // children
        left.transform(leftExpr -> callback.call(new Add(leftExpr, right)));
        right.transform(rightExpr -> callback.call(new Add(left, rightExpr)));
    }

    // copy
    @Override
    public Expr copy() {
        return new Add(left.copy(), right.copy());
    }
    // compareTo
    @Override
    public int compareTo(Expr other) {
        if (other instanceof Add otherAdd) {
            int leftCompare = left.compareTo(otherAdd.left);
            if (leftCompare == 0) {
                return right.compareTo(otherAdd.right);
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
        // subtraction
        if (right instanceof Mul mul) {
            if (mul.left instanceof Num num) {
                if (num.value == -1.0) {
                    return left.str() + " - " + mul.right.str();
                }
            }
        }
        return left.str() + " + " + right.str();
    }

    // debug
    @Override
    public String debugStr() {
        return "ADD(" + left.debugStr() + "," + right.debugStr() + ")";
    }
}