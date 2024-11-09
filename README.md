# algebra
This project is about building an algebra system on Java.
It deals with algebraic expressions, especially how simplify them.

In particular, an algebraic expression contains the following building blocks:
1. `numbers` (rationals) like 1, -2 or 0.5
2. `variables` like x, y or alpha
3. `additions`, like 1 + 2 or (x + 2) + 4
4. `multiplications`, like 2 * 3 or (x + 3) * 2 * 3
5. `exponential function`, like exp(1) or exp(x + 1)
6. `logarithm function`, like log(1) or log(x + 1)

Note that negation, subtractions, divisions and exponentiation not atomic building blocks of the algebraic system,
since they can be derived from the above:
- -x -> -1 * x
- 1 - x -> 1 + (-1 * x)
- 2 / x -> 2 * exp(log(x) * -1)
- x^y -> exp(log(x) * y)
## Simplifying
The key objective of this project is to develop an algorithm to simplify given algebraic expressions.

### Complexity
Probably everyone would agree that `2 + 2 * x` is a "simplification" to `1 + x + 1 + x`. But what about more complex expressions? What does it mean for an expression to be simplified?

I came up with the following definition:

The complexity of an algebraic expression is determined by two factors,
1. The count of subexpressions in an expression,

and if they are equal

2. The count of occurances of variables in an expression

The expression `(1 + x) + x + x` has the subexpressions
- `((1 + x) + x) + x`
- `(1 + x) + x`
- `(1 + x)`
- `1`
- `x`
- `x`
- `x`

so a total of 7 expressions, whereas `1 + 3 * x` has only 5.

Since both expressions are equivalent, we say that `1 + 3 * x` is a simplification for `(1 + x) + x + x`.

Now consider `x + x`.
Most people would instantly "simplify" the expression to `2 * x`.
The first rule however gives both expressions the same complexity, so that's where the second rule comes in place.

The second rule "motivates" to reduce the amounts of occurances of a variable, usually introducing an additional number.

### Finding the minimum
Since every algebraic expression has a finite complexity,
the question for a minimal, equivalent expression is well asked.
However, we can't expect uniqueness, see `x + 1`and `1 + x`.

The problem of finding a minimum is,
that there are infinitely many equivalent expressions to a given one.

For example `x` is equivalent to `x + 0` is equivalent to `x + 0 + 0` and so on...

So in order to do computation, we would have to limit ourselves to a finite subset of equivalent expressions.

What one could do is, to only consider specific _transformations_ of the initial expression that keep the expression the same, for example
- `x` = `x + 0`
- `x + y` = `y + x`
- `(x + y) * z` = `y * z + x * z`

and so on.

I decided to include the following transformations to my algorithm:
- `x + y` = `y + x` and `x * y` = `y * x` (commutativity)
- `x + x` = `2 * x`, `a * x + x` = `(a + 1) * x` and `a * x + b * x` = `(a + b) * x` (promotion)
- `x + 0` = `x`, `x * 1` = `x` (neutralize) !transform only from left to right
- `exp(log(x))` = `x`, `log(exp(x))` = `x` (cancel) !transform only from left to right

Now that we have a finite (and non recursive) set of transformations, applying these transformations on the initial expression or results, we end up with a finite amount of expressions.

Now we can simply choose the one with the lowest complexity and we're done!

### Performance...
Well. The problem we now have is performance.
The problem with this approach is, that it's runtime complexity is exponential.

Let's say `...` is an expression.
Adding only one extra expression like `... + a` effectively doubles the amount of possible equivalent expressions since each equivalence for `...` can be put once like this `... + a` and once like this `a + ...`.

We can heavily reduce the amount of expressions to evaluate by discarding certain transformations very early. We can for example discard unnecessary permutation like in the example above (this always works if `...` doesn't contain the variable `a`).
However, the runtime remains exponential.

Right now, I haven't found a solution for this without losing a lot of certainty about the result.