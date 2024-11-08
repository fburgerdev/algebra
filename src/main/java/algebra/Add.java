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
        return new Add(left.copy(), right.copy());
    }
    // equals
    @Override
    public boolean equals(Expr other) {
        if (other instanceof Add otherAdd) {
            return left.equals(otherAdd.left) && right.equals(otherAdd.right);
        }
        return false;
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