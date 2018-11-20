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
    public boolean validator() throws Exception {

//        System.out.println(DFA.getAlphabet());
//        System.out.println(DFA.getFinal_states());
//        System.out.println(DFA.getStart());
//        System.out.println(DFA.getStates());
//        System.out.println(DFA.getTransitions());
        for(Character input: DFA.getAlphabet()){
            if(input.equals('$'))return false;
        }


        Set<String> outputs = new HashSet<>();

        for (String state:DFA.getStates()) {
            if(DFA.getTransitions().get(state) == null) throw new Exception("There is null transition");

            if(DFA.getTransitions().get(state).size() != DFA.getAlphabet().size()) throw new Exception("Not enough input for all states!");

            for(Character input: DFA.getAlphabet()){
                outputs.addAll(DFA.getTransitions().get(state).get(input));
            }
        }

        if(!outputs.containsAll(DFA.getFinal_states()))
            throw new Exception("Final State not reached");

        return true;
    }
}
