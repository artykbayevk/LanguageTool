package AST.RegularExpressions;

import AST.AutomataAST;
import AST.RegularExpression;

import java.util.List;

public class SelectionRegularExpression implements RegularExpression {

    public RegularExpression leftHandSide, rightHandSide;

    public SelectionRegularExpression(RegularExpression leftHandSide, RegularExpression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    @Override
    public void printElements() {
        System.out.print("(");
        leftHandSide.printElements();
        System.out.print(" or ");
        rightHandSide.printElements();
        System.out.print(")");
    }


    @Override
    public String value() {
        return leftHandSide.value() +' '+rightHandSide.value();
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
