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
        return 1 + left.countExprs() + right.countExprs();
    }
    @Override
    public int countVars() {
        return left.countVars() + right.countVars();
    }

    // transform
    @Override
    public void transform(ExprCallback callback) {
        // commute
        callback.call(new Add(right, left));

        // promote
        if (left.compareTo(right) == 0) {
            callback.call(new Mul(new Num(2.0), right));
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

    // debug
    @Override
    public String debugStr() {
        return "ADD(" + left.debugStr() + "," + right.debugStr() + ")";
    }
}