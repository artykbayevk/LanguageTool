package AST.RegularExpressions;

import AST.RegularExpression;

public class EmptyRegularExpression implements RegularExpression {
    public Character empty = '$';

    public EmptyRegularExpression() {}

    @Override
    public void printElements() {
        System.out.print(empty);
    }
}
