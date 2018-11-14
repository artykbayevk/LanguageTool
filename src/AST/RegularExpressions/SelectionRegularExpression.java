package AST.RegularExpressions;

import AST.RegularExpression;

import java.util.List;

public class SelectionRegularExpression implements RegularExpression {
    public List<RegularExpression> variables;


    public SelectionRegularExpression(List<RegularExpression> variables) {
        this.variables = variables;
    }



}
