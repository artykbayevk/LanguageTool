package AST.RegularExpressions;

import AST.RegularExpression;

import java.util.List;

public class SelectionRegularExpression implements RegularExpression {

    public RegularExpression leftHandSide, rightHandSide;

    public SelectionRegularExpression(RegularExpression leftHandSide, RegularExpression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    
}
