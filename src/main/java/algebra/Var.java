package algebra;

public class Var extends Expr {
    // name
    public String name;

    // constructor
    public Var(String name) {
        this.name = name;
    }

    // complexity
    @Override
    public int countExprs() {
        return 1;
    }
    @Override
    public int countVars() {
        return 1;
    }

    // transform
    @Override
    public void transform(Callback callback) {
        // pass
    }

    // copy
    @Override
    public Expr copy() {
        return new Var(name);
    }
    // equals
    @Override
    public boolean equals(Expr other) {
        if (other instanceof Var otherVar) {
            return name.equals(otherVar.name);
        }
        return false;
    }
    // compareTo
    @Override
    public int compareTo(Expr other) {
        if (other instanceof Var otherVar) {
            return name.compareTo(otherVar.name);
        }
        else {
            return compareSubtypes(other);
        }
    }

    // debug
    @Override
    public String debugStr() {
        return name;
    }
}