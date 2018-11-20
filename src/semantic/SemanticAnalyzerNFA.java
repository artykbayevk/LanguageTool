package semantic;

import AST.AutomataAST;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SemanticAnalyzerNFA implements SemanticAnalyzer {

    AutomataAST NFA;

    public SemanticAnalyzerNFA(AutomataAST NFA) {
        this.NFA = NFA;
    }

    @Override
    public void validator()throws Exception {
        Set<String> outputs = new HashSet<>();

        if(!NFA.getStates().contains(NFA.getStart())) throw new Exception("There is no start states");

        for (Map.Entry<String, Map<Character,Set<String>>> pair: NFA.getTransitions().entrySet()) {
            for (Map.Entry<Character, Set<String>> transition: pair.getValue().entrySet()) {
                outputs.addAll(transition.getValue());
            }
        }


        if(!outputs.containsAll(NFA.getFinal_states())) throw new Exception("Doesnt reach to final states");



        System.out.println("Semantic is OK!");
    }
}
