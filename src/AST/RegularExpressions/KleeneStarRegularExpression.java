package AST.RegularExpressions;

import AST.AutomataAST;
import AST.RegularExpression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KleeneStarRegularExpression implements RegularExpression {
    public RegularExpression regularExpressionGroup;

    public KleeneStarRegularExpression(RegularExpression regularExpressionGroup) {
        this.regularExpressionGroup = regularExpressionGroup;
    }

    @Override
    public void printElements() {
        regularExpressionGroup.printElements();
        System.out.print("*");
    }


    @Override
    public String value() {
        return regularExpressionGroup.value()+"star";
    }

    @Override
    public RegularExpression itself() {
        return this;
    }


    @Override
    public AutomataAST convertToNFA() {
        AutomataAST NFA = regularExpressionGroup.convertToNFA();

        //update alphabet
        Set<Character> main_alphabet = new HashSet<>();
        main_alphabet.addAll(NFA.getAlphabet());
        main_alphabet.add('$');

        //update final states
        Set<String> updated_final_states  = new HashSet<>();
        updated_final_states.addAll(NFA.getFinal_states());
        updated_final_states.add(NFA.getStart());


        //create new output state for transitions
        Set<String> updatedTransition = new HashSet<>();
        updatedTransition.add(NFA.getStart());

        //creating map for output transition
        Map<Character, Set<String>> output_transition = new HashMap<>();
        output_transition.put('$',updatedTransition);


        // add new node for returning to the start state
        Map<String, Map<Character, Set<String>>> main_transitions = new HashMap<>();

        // put loop node
        for (String final_state:NFA.getFinal_states()) {
            main_transitions.put(final_state, output_transition);
        }


        // put other transitions
        for (Map.Entry<String, Map<Character, Set<String>>> pair:NFA.getTransitions().entrySet()) {
            main_transitions.put(pair.getKey(), pair.getValue());
        }

        return new AutomataAST(main_alphabet, NFA.getStates(), main_transitions, updated_final_states, NFA.getStart());
    }
}
