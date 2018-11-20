package semantic;

import AST.RegularExpression;

public class SemanticAnalyzerRegExp implements SemanticAnalyzer {

    RegularExpression regExp;

    public SemanticAnalyzerRegExp(RegularExpression regExp) {
        this.regExp = regExp;
    }

    @Override
    public boolean validator()throws Exception {
        return false;
    }
}
