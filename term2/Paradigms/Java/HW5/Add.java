package expression;


/**
 * Created by vadim on 19.03.2017.
 */
public class Add extends BinaryOperator {
    public Add(AllExpressions first, AllExpressions second) {
        super(first, second);
    }

    public int calc(int a, int b){
        return a+b;
    }

    public double calcd(double a, double b){
        return a+b;
    }
}
