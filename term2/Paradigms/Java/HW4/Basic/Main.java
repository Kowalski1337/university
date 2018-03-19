package expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Main {

    public static void main(String[] args) {
        assert args.length > 0: "WHERE IS MY VALUE, DUDE?!";
        double arg = Double.parseDouble(args[0]);
        double x = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)
        ).evaluate(arg);

        x *= 1000;
        x = (int)x;
        System.out.print(x/1000);

    }
}