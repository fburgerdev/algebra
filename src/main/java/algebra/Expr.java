package algebra;

public abstract class Expr implements Comparable<Expr> {
    // complexity
    public abstract int countExprs();
    public abstract int countVars();
    public int complexity() {
        return countExprs() * 1024 + countVars();
    }

    // transform
    public abstract void transform(Callback callback);
    public int countTransforms() {
        transformCount = 0;
        transform(() -> {
            transformCount += 1;
        });
        return transformCount;
    }
    
    // copy, equals
    public abstract Expr copy();
    public abstract boolean equals(Expr other);
    public int compareSubtypes(Expr other) {
        int thisScore = 0;
        if (this instanceof Num) {
            thisScore = 1;
        }
        else if (this instanceof Var) {
            thisScore = 2;
        }
        if (this instanceof Add) {
            thisScore = 3;
        }
        else if (this instanceof Mul) {
            thisScore = 4;
        }
        else if (this instanceof Exp) {
            thisScore = 5;
        }
        else if (this instanceof Log) {
            thisScore = 6;
        }
        int otherScore = 0;
        if (other instanceof Num) {
            otherScore = 1;
        }
        else if (other instanceof Var) {
            otherScore = 2;
        }
        if (other instanceof Add) {
            otherScore = 3;
        }
        else if (other instanceof Mul) {
            otherScore = 4;
        }
        else if (other instanceof Exp) {
            otherScore = 5;
        }
        else if (other instanceof Log) {
            otherScore = 6;
        }

        return Integer.compare(thisScore, otherScore);
    }

    // debug
    public abstract String debugStr();

    private int transformCount = 0;
}