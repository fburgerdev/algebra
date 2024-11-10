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
    // simplify
    @Override
    public Expr simplify() {
        return new Var(name);
    }
    // transform
    @Override
    public void transform(ExprCallback callback) {
        // pass
    }

    // copy
    @Override
    public Expr copy() {
        return new Var(name);
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

    // str
    @Override
    public String str() {
        return name;
    }
    // debugStr
    @Override
    public String debugStr() {
        return name;
    }
}