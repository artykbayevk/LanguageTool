package AST.RegularExpressions;

import AST.AutomataAST;
import AST.RegularExpression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static util.Config.getGlobal_counter;

public class EmptyRegularExpression implements RegularExpression {
    public Character empty = '$';

    public EmptyRegularExpression() {}

    @Override
    public void printElements() {
        System.out.print(empty);
    }


    @Override
    public String value() {
        return empty.toString();
    }

    @Override
    public RegularExpression itself() {
        return this;
    }


    @Override
    public AutomataAST convertToNFA() {


        // Creating character alphabet for NFA
        Set<Character> alphabet = new HashSet<>();
        alphabet.add(empty);

        //Start and final states
        String start_state = "q"+ getGlobal_counter();

        Set<String> final_states = new HashSet<>();
        final_states.add("q"+ getGlobal_counter());

        //Creating all states
        Set<String> states = new HashSet<>();
        states.add(start_state);
        states.addAll(final_states);

        Map<Character, Set<String>> output_transition = new HashMap<>();
        output_transition.put(empty, final_states);

        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();
        transitions.put(start_state, output_transition);

        return new AutomataAST(alphabet, states, transitions, final_states, start_state);
    }
}
