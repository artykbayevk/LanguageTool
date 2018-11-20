package semantic;

import AST.AutomataAST;

import java.util.HashSet;
import java.util.Set;

public class SemanticAnalyzerDFA implements SemanticAnalyzer {


    AutomataAST DFA;

    public SemanticAnalyzerDFA(AutomataAST DFA) {
        this.DFA = DFA;
    }

    @Override
    public void validator() throws Exception {

        for(Character input: DFA.getAlphabet()){
            if(input.equals('$')) throw new Exception("There is empty input!");
        }


        Set<String> outputs = new HashSet<>();

        if (!DFA.getStates().contains(DFA.getStart())) throw new Exception("There is no start state");

        for (String state:DFA.getStates()) {
            if(DFA.getTransitions().get(state) == null) throw new Exception("There is null transition");

            if(DFA.getTransitions().get(state).size() != DFA.getAlphabet().size()) throw new Exception("Not enough input for all states!");

            for(Character input: DFA.getAlphabet()){
                outputs.addAll(DFA.getTransitions().get(state).get(input));
            }
        }

        if(!outputs.containsAll(DFA.getFinal_states()))
            throw new Exception("Final State not reached");


        System.out.println("Semantic is OK!");
    }
}
