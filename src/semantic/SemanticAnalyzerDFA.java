package semantic;

import AST.AutomataAST;

public class SemanticAnalyzerDFA implements SemanticAnalyzer {


    AutomataAST DFA;

    public SemanticAnalyzerDFA(AutomataAST DFA) {
        this.DFA = DFA;
    }

    @Override
    public boolean validator() {
        return false;
    }
}
