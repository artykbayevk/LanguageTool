package AST;

import java.util.Map;
import java.util.Set;

public class AutomataAST {
    private Set<Character> alphabet;
    private Set<String> states;
    private Map<String, Map<Character, Set<String>>> transitions;
    private Set<String> final_states;
    private String start;

    public AutomataAST(Set<Character> alphabet, Set<String> states, Map<String, Map<Character, Set<String>>> transitions, Set<String> final_states, String start) {
        this.alphabet = alphabet;
        this.states = states;
        this.transitions = transitions;
        this.final_states = final_states;
        this.start = start;
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public Set<String> getStates() {
        return states;
    }

    public Map<String, Map<Character, Set<String>>> getTransitions() {
        return transitions;
    }

    public Set<String> getFinal_states() {
        return final_states;
    }

    public String getStart() {
        return start;
    }


}