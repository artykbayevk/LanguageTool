package AST;

import AST.RegularExpressions.EmptyRegularExpression;
import parsing.RegExpParser;

import java.lang.reflect.Array;
import java.net.Inet4Address;
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
                if(intro_pair.getValue().contains("qTrap")) intro_pair.getValue().remove("qTrap");
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




        // delete all qTrap states
        if(converted_states.contains("qTrap")) converted_states.remove("qTrap");
        if(converted_transitions.containsKey("qTrap"))converted_transitions.remove("qTrap");

        //deleting all empty transitions
        for (Map.Entry<String, Map<String, Set<String>>> pair: converted_transitions.entrySet()){
            Map<String, Set<String>> overriding_values = new HashMap<>();
            for (Map.Entry<String,Set<String>> entry_pair:pair.getValue().entrySet()) {
                if(entry_pair.getValue().size() != 0) overriding_values.put(entry_pair.getKey(), entry_pair.getValue());
            }

            pair.setValue(overriding_values);
        }


        // combine all loops --> q1 = [a = [q1], b = [q1]] ===> q1 = [a|b = [q1]]
        // create a union transitions between each other
        for (Map.Entry<String, Map<String, Set<String>>> pair: converted_transitions.entrySet()) {
            if(!pair.getKey().equals("q_conv_s") ||pair.getKey().equals("q_conv_f")){
                pair.setValue(convertLoopTransition(pair.getKey(), pair.getValue()));
                pair.setValue(convertUnionTransition(pair.getKey(), pair.getValue()));
            }
        }


        System.out.println();

        for (Map.Entry<String, Map<String, Set<String>>> pair: converted_transitions.entrySet()) {
            System.out.print("State: "+pair.getKey() + " on input : ");
            for (Map.Entry<String, Set<String>> entry_pair:pair.getValue().entrySet()) {
                System.out.print("CHAR="+ entry_pair.getKey() + " OUTPUT=" + entry_pair.getValue() + " ; ");
            }
            System.out.println();
        }


        System.out.println();
        System.out.println("There our transition map changed by adding new START STATE: q_conv_s and new FINAL STATE: q_conv_f");
        System.out.println("All loops and unions show in above transitions");



        return new EmptyRegularExpression();
    }

    Map<String,Set<String>> convertLoopTransition(String input_state, Map<String, Set<String>> output_transition){
        Map<String, Set<String>> convertedOutputTransition = new HashMap<>();
        String reg = "";

        Set<String> remove_chars = new HashSet<>();

        for (Map.Entry<String, Set<String>> pair: output_transition.entrySet()) {
            if(pair.getValue().contains(input_state)){
                if(reg.length() == 0){
                    reg += pair.getKey();
                    remove_chars.add(pair.getKey());
                }
                else if(reg.length() >= 1) {
                    reg = "("+reg+"|"+pair.getKey()+")";
                    remove_chars.add(pair.getKey());
                }
            }
        }

        if(reg.length() == 1) return output_transition;


        if(reg.length() == 0) return output_transition;



        for(String remove_ch: remove_chars){
            output_transition.remove(remove_ch);
        }

        Set<String> final_state = new HashSet<>();
        final_state.add(input_state);
        output_transition.put(reg, final_state);



        return output_transition;
    }

    Map<String,Set<String>> convertUnionTransition(String input_state, Map<String, Set<String>> output_transition){
        Map<String, Set<String>> convertedOutputTransition = new HashMap<>();


        Map<String, Integer> freq_of_output = new HashMap<>();

        //finding all output states
        for(Map.Entry<String, Set<String>> pair: output_transition.entrySet()){
            List<String> tmp = new ArrayList<>(pair.getValue());
            if(freq_of_output.containsKey(tmp.get(0))){
                int index = freq_of_output.get(tmp.get(0));
                index++;
                freq_of_output.put(tmp.get(0), index);
            }else{
                freq_of_output.put(tmp.get(0), 1);
            }
        }
        freq_of_output.remove(input_state);


        //concantinating all this shit

        Map<String, String> state_to_regexp = new HashMap<>();
        Set<String> removing_chars = new HashSet<>();

        for(Map.Entry<String, Set<String>> pair: output_transition.entrySet()){
            List<String> value_list = new ArrayList<>(pair.getValue());
            String value = value_list.get(0);
            if(freq_of_output.containsKey(value)){
                if(freq_of_output.get(value) > 1){
                    if(state_to_regexp.containsKey(value)){
                        String reg = state_to_regexp.get(value);
                        removing_chars.add(pair.getKey());
                        reg = "("+reg+"|"+pair.getKey()+")";
                        state_to_regexp.put(value, reg);
                    }else{
                        state_to_regexp.put(value, pair.getKey());
                        removing_chars.add(pair.getKey());
                    }
                }
            }

        }


        for(Map.Entry<String, String> pair: state_to_regexp.entrySet()){
            Set<String>out_set= new HashSet<>();
            out_set.add(pair.getKey());
            output_transition.put(pair.getValue(), out_set);
        }

        

        for (String chars_:removing_chars) {
            output_transition.remove(chars_);
        }

        return output_transition;

    }

}


