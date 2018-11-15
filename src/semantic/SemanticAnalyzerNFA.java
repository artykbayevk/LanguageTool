package semantic;

import AST.AutomataAST;

public class SemanticAnalyzerNFA implements SemanticAnalyzer {

    AutomataAST NFA;

    public SemanticAnalyzerNFA(AutomataAST NFA) {
        this.NFA = NFA;
    }

    @Override
    public boolean validator() {
        return false;
    }
}
