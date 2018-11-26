package AST.RegularExpressions;

import AST.AutomataAST;
import AST.RegularExpression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import util.Config;

public class ConcatRegularExpression implements RegularExpression {

    public RegularExpression leftHandSide, rightHandSide;

    public ConcatRegularExpression(RegularExpression leftHandSide, RegularExpression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }


    @Override
    public void printElements() {
        System.out.print("(");
        leftHandSide.printElements();
        System.out.print(" and ");
        rightHandSide.printElements();
        System.out.print(")");
    }

    @Override
    public String value() {
        return leftHandSide.value() + " and "+ rightHandSide.value();
    }


    @Override
    public RegularExpression itself() {
        return this;
    }


    @Override
    public AutomataAST convertToNFA() {
        AutomataAST leftNFA = leftHandSide.convertToNFA();
        AutomataAST rightNFA = rightHandSide.convertToNFA();

        //creating new alphabet
        Set<Character> main_alphabet = new HashSet<>();
        main_alphabet.addAll(leftNFA.getAlphabet());
        main_alphabet.addAll(rightNFA.getAlphabet());
        main_alphabet.add('$');

        //assigning start state
        String main_start_state = leftNFA.getStart();

        //assigning new final states
        Set<String> main_final_states = new HashSet<>();
        main_final_states.addAll(rightNFA.getFinal_states());


        //creating new states set
        Set<String> states = new HashSet<>();
        states.addAll(leftNFA.getStates());
        states.addAll(rightNFA.getStates());

        //Creating new transitions map
        Map<String, Map<Character, Set<String>>> main_transitions = new HashMap<>();


        //put all contained transitions
        for (String pair:leftNFA.getStates()) {
            if(leftNFA.getTransitions().containsKey(pair)) {
                main_transitions.put(pair, leftNFA.getTransitions().get(pair));
            }
        }

        for (String pair:rightNFA.getStates()) {
            if(rightNFA.getTransitions().containsKey(pair)) {
                main_transitions.put(pair, rightNFA.getTransitions().get(pair));
            }
        }


        //create connection transition
        Set<String> output_state = new HashSet<>();
        output_state.add(rightNFA.getStart());

        for (Map.Entry<String, Map<Character, Set<String>>> pair: main_transitions.entrySet()) {
            for (String final_state_of_left:leftNFA.getFinal_states()) {
                if(pair.getKey().equals(final_state_of_left)){
                    if(pair.getValue().containsKey('$')){
                        pair.getValue().get('$').addAll(output_state);
                    }else{
                        pair.getValue().put('$',output_state);
                    }
                }
            }
        }



        for (String final_state_of_left: leftNFA.getFinal_states()){
            if(!main_transitions.containsKey(final_state_of_left)){
                Map<Character, Set<String>> out_states = new HashMap<>();
                out_states.put('$', output_state);
                main_transitions.put(final_state_of_left, out_states);
            }
        }




        return new AutomataAST(main_alphabet, states, main_transitions, main_final_states, main_start_state);
    }
}
