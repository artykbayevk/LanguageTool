package AST.RegularExpressions;

import AST.AutomataAST;
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

    @Override
    public RegularExpression itself() {
        return this;
    }


    @Override
    public AutomataAST convertToNFA() {
        return null;
    }
}
