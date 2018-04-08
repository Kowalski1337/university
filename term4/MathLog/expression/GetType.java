package expression;

public class GetType {
    static int getType(String s){
        switch (s){
            case "&":
                return 3;
            case "|":
                return 2;
            case "->":
                return 1;
            case "variable":
                return 5;
            case "!":
                return 4;
                default:
                    return 0;
        }
    }
}
