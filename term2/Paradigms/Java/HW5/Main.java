package expression;

/**
 * Created by vadim on 20.03.2017.
 */
public class Main {

    public static void main(String[] args) {
        assert args.length > 0: "WHERE IS MY VALUE, DUDE?!";
        int arg = Integer.parseInt(args[0]);
        System.out.println(new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("y")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("z")
                        )
                ),
                new Const(1)

        ).evaluate(arg));
    }
}