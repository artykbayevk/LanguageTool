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

    public void execute(String word) throws Exception{
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

    public void printAutomataAST(){
        System.out.println();
        System.out.println("Automata Structure:");

        System.out.println("Start state: "+ start);
        System.out.print("Final state(s): ");
        for (String final_state: final_states){
            System.out.print(final_state+' ');
        }
        System.out.println();

        System.out.print("All states: ");
        for (String state: states){
            System.out.print(state+' ');
        }
        System.out.println();

        System.out.print("Alphabet: ");
        for (Character input:alphabet) {
            System.out.print(input.toString()+' ');
        }
        System.out.println();

        for (Map.Entry<String, Map<Character, Set<String>>> pair:transitions.entrySet()) {
            System.out.print("State: "+pair.getKey()+" on inputs: ");
            for (Map.Entry<Character, Set<String>> transition: pair.getValue().entrySet()){
                System.out.print(transition.getKey()+ " : ");
                for (String out_state:transition.getValue()) {
                    System.out.print(out_state+' ');
                }
            }
            System.out.println();
        }

        System.out.println();
    }
}