package AST.RegularExpressions;

import AST.RegularExpression;

import java.util.List;

public class ConcatRegularExpression implements RegularExpression {

    public RegularExpression leftHandSide, rightHandSide;

    public ConcatRegularExpression(RegularExpression leftHandSide, RegularExpression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }
}