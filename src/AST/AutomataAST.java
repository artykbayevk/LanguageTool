package AST;

import java.lang.reflect.Array;
import java.util.*;

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

    public RegularExpression convertToRegExp(){
        // writing function that convert NFA to GNFA
        // then convert this GNFA to regExp

        System.out.println("Current NFA/DFA");
        this.printAutomataAST();


        // creating all need variables
        String converter_start_state = "q_conv_s";
        String converted_final_state = "q_conv_f";
        Set<String> converted_states = new HashSet<>();
        converted_states.addAll(this.states);
        converted_states.add(converter_start_state);
        converted_states.add(converted_final_state);


        // creating new transition map and new alphabet
        Map<String, Map<String, Set<String>>> converted_transitions = new HashMap<>();
        Set<String> converted_alphabet =new HashSet<>();
        for (Character ch:this.alphabet) {
            converted_alphabet.add(ch.toString());
        }

        //put together all transitions
        for (Map.Entry<String, Map<Character, Set<String>>> pair:this.transitions.entrySet()) {
            Map<String, Set<String>> out_transition = new HashMap<>();
            for (Map.Entry<Character, Set<String>> intro_pair:pair.getValue().entrySet()) {
                out_transition.put(intro_pair.getKey().toString(), intro_pair.getValue());
            }
            converted_transitions.put(pair.getKey(), out_transition);
        }

        //add new transitions
        Map<String, Set<String>> new_transition = new HashMap<>();
        Set<String> new_output = new HashSet<>();
        new_output.add(this.getStart());

        new_transition.put("$", new_output);

        //concantenate start states
        converted_transitions.put(converter_start_state, new_transition);


        for (String final_state:this.final_states) {
            if(converted_transitions.containsKey(final_state)){
                if(converted_transitions.get(final_state).containsKey("$")){
                    converted_transitions.get(final_state).get("$").add(converted_final_state);
                }else{
                    Set<String> converted_f_state = new HashSet<>();
                    converted_f_state.add(converted_final_state);
                    converted_transitions.get(final_state).put("$",converted_f_state);
                }
            }else{
                Map<String, Set<String>> out_transition  = new HashMap<>();
                Set<String> converted_f_state = new HashSet<>();
                converted_f_state.add(converted_final_state);
                out_transition.put("$",converted_f_state);
                converted_transitions.put(final_state, out_transition);
            }
        }



        //HERE WE HAVE NEW STATES THAT CONNECTED WITH EMPTY TRANSITION


        System.out.println(converted_transitions);
        System.out.println(converted_states);
        System.out.println(converted_final_state);
        System.out.println(converter_start_state);
        System.out.println(converted_alphabet);







        return null;
    }
}