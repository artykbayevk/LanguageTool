package AST;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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

    public void execute(String type, String word) throws Exception{
        if (!type.equals("DFA")){
            throw new Exception("This it not a DFA");
        }

        List<Character> word_sep = new ArrayList<Character>();
        char word_arr[] = word.toCharArray();
        for (int i = 0; i <word_arr.length ; i++) {
            word_sep.add(word_arr[i]);
        }


        if(!this.alphabet.containsAll(word_sep)) throw new Exception("This words can't run on this DFA");
        String current = this.start;

        for (Character input :word_sep) {
            current = this.transitions.get(current).get(input).toArray()[0].toString();
        }

        if(this.getFinal_states().contains(current)){
            System.out.println("Accepted word");
        }else{
            System.out.println("Not accepted word");
        }


    }
}