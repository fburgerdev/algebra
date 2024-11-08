package algebra;

public class Variable implements Expr {
    // name
    public String name;

    // constructor
    public Variable(String name) {
        this.name = name;
    }
    // complexity
    @Override
    public double complexity() {
        return 0.001;
    }
    // simplify
    @Override
    public Expr simplify() {
        return this;
    }
    // floatLeft
    @Override
    public void floatLeft() {
        // pass
    }

    // debugStr
    @Override
    public String debugStr() {
        return "VAR " + name;
    }
}