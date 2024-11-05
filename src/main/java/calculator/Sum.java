package calculator;

public class Sum implements Expr {
    // left, right
    public Expr left;
    public Expr right;

    // constructor
    public Sum(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }
    // eval
    public Number eval(Number left, Number right) {
        return new Number(left.value + right.value);
    }
    // simplify
    @Override
    public Expr simplify() {
        left = left.simplify();
        right = right.simplify();
        if (left instanceof Number leftNum && right instanceof Number rightNum) {
            return new Number(leftNum.value + rightNum.value);
        }
        return this;
    }
    // floatLeft
    @Override
    public void floatLeft() {
        // rebalance
        if ((left instanceof Sum) && (right instanceof Sum sumRight)) {
            left = new Sum(left, sumRight.left);
            right = sumRight.right;
        }
        // swap
        else if (!(left instanceof Sum) && (right instanceof Sum)) {
            Expr temp = left;
            left = right;
            right = temp;
        }

        // cascade
        left.floatLeft();
        right.floatLeft();

        // leafSum
        Sum leafSum = this;
        while (leafSum.left instanceof Sum sumLeft) {
            leafSum = sumLeft;
        }
        // float numbers
        if (right instanceof Number numRight) {
            if (!(leafSum.left instanceof Number)) {
                Expr temp = leafSum.left;
                leafSum.left = numRight;
                right = temp;
            }
            else if (!(leafSum.right instanceof Number)) {
                Expr temp = leafSum.right;
                leafSum.right = numRight;
                right = temp;
            }
        }
    }

    // debugStr
    @Override
    public String debugStr() {
        String leftString = left.debugStr().replaceAll("\n", "\n  ");
        String rightString = right.debugStr().replaceAll("\n", "\n  ");
        return "ADD\n  " + leftString + "\n  " + rightString;
    }
}