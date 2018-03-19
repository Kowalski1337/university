package expression;

/**
 * Created by vadim on 20.03.2017.
 */
abstract public class BinaryOperator implements AllExpressions {
    private AllExpressions arg1;
    private AllExpressions arg2;

    protected BinaryOperator(AllExpressions a, AllExpressions b){
        arg1 = a;
        arg2 = b;
    }

    public int evaluate(int x, int y, int z){
        return calc(arg1.evaluate(x, y, z), arg2.evaluate(x, y, z));
    }

    public double evaluate(double arg){
        return calcd(arg1.evaluate(arg), arg2.evaluate(arg));
    }

    abstract public double calcd(double a, double b);

    public int evaluate(int arg){
        return calc(arg1.evaluate(arg), arg2.evaluate(arg));
    }

    abstract public int calc(int a, int b);
}
