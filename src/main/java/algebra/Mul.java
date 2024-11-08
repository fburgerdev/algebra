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
        return 1 + left.countExprs() + right.countExprs();
    }
    @Override
    public int countVars() {
        return left.countVars() + right.countVars();
    }

    // transform
    @Override
    public void transform(Callback callback) {
        // commute
        Expr temp = left;
        left = right;
        right = temp;
        callback.call();
        right = left;
        left = temp;

        // children
        left.transform(callback);
        right.transform(callback);
    }

    // copy
    @Override
    public Expr copy() {
        return new Mul(left.copy(), right.copy());
    }
    // equals
    @Override
    public boolean equals(Expr other) {
        if (other instanceof Mul otherMul) {
            return left.equals(otherMul.left) && right.equals(otherMul.right);
        }
        return false;
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

    // debug
    @Override
    public String debugStr() {
        return "MUL(" + left.debugStr() + "," + right.debugStr() + ")";
    }
}