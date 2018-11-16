package AST.RegularExpressions;

import AST.RegularExpression;

public class KleeneStarRegularExpression implements RegularExpression {
    public RegularExpression regularExpressionGroup;

    public KleeneStarRegularExpression(RegularExpression regularExpressionGroup) {
        this.regularExpressionGroup = regularExpressionGroup;
    }

    @Override
    public void printElements() {
        regularExpressionGroup.printElements();
        System.out.print("*");
    }


    @Override
    public String value() {
        return regularExpressionGroup.value();
    }
}
