package AST.RegularExpressions;

import AST.RegularExpression;

import java.util.List;

public class ConcatRegularExpression implements RegularExpression {

    public List<RegularExpression> regExps;

    public ConcatRegularExpression(List<RegularExpression> regExps) {
        this.regExps = regExps;
    }
}
