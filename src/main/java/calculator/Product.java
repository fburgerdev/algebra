package calculator;

public class Product implements Expr {
    // left, right
    public Expr left;
    public Expr right;

    // constructor
    public Product(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }
    // eval
    public Number eval(Number left, Number right) {
        return new Number(left.value * right.value);
    }
    // simplify
    @Override
    public Expr simplify() {
        left = left.simplify();
        right = right.simplify();
        if (left instanceof Number leftNum && right instanceof Number rightNum) {
            return new Number(leftNum.value * rightNum.value);
        }
        return this;
    }
    // floatLeft
    @Override
    public void floatLeft() {

    }
    
    // debugStr
    @Override
    public String debugStr() {
        String leftString = left.debugStr().replaceAll("\n", "\n  ");
        String rightString = right.debugStr().replaceAll("\n", "\n  ");
        return "MUL\n  " + leftString + "\n  " + rightString;
    }
}