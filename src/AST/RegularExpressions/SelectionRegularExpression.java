package AST.RegularExpressions;

import AST.AutomataAST;
import AST.RegularExpression;
import util.Config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SelectionRegularExpression implements RegularExpression {

    public RegularExpression leftHandSide, rightHandSide;


    public SelectionRegularExpression(RegularExpression leftHandSide, RegularExpression rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    @Override
    public void printElements() {
        System.out.print("(");
        leftHandSide.printElements();
        System.out.print(" or ");
        rightHandSide.printElements();
        System.out.print(")");
    }


    @Override
    public String value() {
        return leftHandSide.value() +" or "+rightHandSide.value();
    }


    @Override
    public RegularExpression itself() {
        return this;
    }


    @Override
    public AutomataAST convertToNFA() {

        AutomataAST leftNFA = leftHandSide.convertToNFA();
        AutomataAST rightNFA = rightHandSide.convertToNFA();


        //Creating new start state
        String main_start_state = "q"+ Config.getGlobal_counter();

        //Combine final states
        Set<String> main_final_states = new HashSet<>();
        main_final_states.addAll(leftNFA.getFinal_states());
        main_final_states.addAll(rightNFA.getFinal_states());

        //Create new states set and put everything from other NFAs
        Set<String> main_states = new HashSet<>();
        main_states.add(main_start_state);
        main_states.addAll(leftNFA.getStates());
        main_states.addAll(rightNFA.getStates());

        //Create new alphabet set
        Set<Character> main_alphabet = new HashSet<>();
        main_alphabet.addAll(leftNFA.getAlphabet());
        main_alphabet.addAll(rightNFA.getAlphabet());
        main_alphabet.add('$');

        //Create new transitions map
        Map<String, Map<Character, Set<String>>> main_transitions = new HashMap<>();
        Map<Character, Set<String>> empty_transitions = new HashMap<>();
        Set<String> output_states = new HashSet<>();


        for (String pair:leftNFA.getStates()) {
            if(leftNFA.getTransitions().containsKey(pair)) {
                output_states.add(pair);
                main_transitions.put(pair, leftNFA.getTransitions().get(pair));
            }
        }

        for (String pair:rightNFA.getStates()) {
            if(rightNFA.getTransitions().containsKey(pair)) {
                output_states.add(pair);
                main_transitions.put(pair, rightNFA.getTransitions().get(pair));
            }
        }


        empty_transitions.put('$', output_states);
        main_transitions.put(main_start_state, empty_transitions);

        return new AutomataAST(main_alphabet, main_states, main_transitions, main_final_states, main_start_state);
    }
}
