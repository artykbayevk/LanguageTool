package AST;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AutomataAST {
    public Set<Character> alphabet;
    public Set<String> states;
    public Map<String, Map<Character, Set<String>>> transitions;
    public Set<String> final_states;
    public String start;

    public AutomataAST(Set<Character> alphabet, Set<String> states, Map<String, Map<Character, Set<String>>> transitions, Set<String> final_states, String start) {
        this.alphabet = alphabet;
        this.states = states;
        this.transitions = transitions;
        this.final_states = final_states;
        this.start = start;
    }
}