package AST.RegularExpressions;

import AST.RegularExpression;

public class EmptyRegularExpression implements RegularExpression {
    public Character empty = '$';

    public EmptyRegularExpression() {}

    public Character getEmpty() {
        return empty;
    }
}
