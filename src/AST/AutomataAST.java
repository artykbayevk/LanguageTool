package AST;

import java.util.List;
import java.util.Map;

public class AutomataAST {
    public List<Character> alphabet;
    public List<String> states;
    public Map<String, Map<String, String>> transitions;
    public List<String> final_states;
    public String start;


    public AutomataAST(List<Character> alphabet, List<String> states, Map<String, Map<String, String>> transitions, List<String> final_states, String start) {
        this.alphabet = alphabet;
        this.states = states;
        this.transitions = transitions;
        this.final_states = final_states;
        this.start = start;
    }

    
}