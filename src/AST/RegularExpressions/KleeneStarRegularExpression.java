package AST.RegularExpressions;

import AST.RegularExpression;

public class KleeneStarRegularExpression implements RegularExpression {
    public RegularExpression regularExpressionGroup;

    public KleeneStarRegularExpression(RegularExpression regularExpressionGroup) {
        this.regularExpressionGroup = regularExpressionGroup;
    }
}
