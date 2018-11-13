package parsing;

import AST.AutomataAST;
import util.Token;

import java.util.*;

public  class AutomataParser {

    List<Token> tokens;

    public AutomataParser(List<Token> tokens) {
        this.tokens = tokens;
    }


    public AutomataAST getAutomataAST(){
        Set<Character> alphabet = new HashSet<>();
        Set<String> states = new HashSet<>();
        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();
        Set<String> final_states = new HashSet<>();
        String begin = "";

        List<String> line = new ArrayList<>();
        boolean begin_state_validator = false;
        boolean final_state_validator = false;


        for (int i = 0; i < tokens.size() ; i++) {
            if(tokens.get(i).val.equals("begin")) {
                begin_state_validator = true;
            }

            if(tokens.get(i).val.equals("final")) {
                final_state_validator = true;
            }


            if(tokens.get(i).val.equals(";")) {


                if(begin_state_validator && !final_state_validator){
                    line = validateRules(line,final_state_validator);
                } else if( final_state_validator){
                    line = validateRules(line, final_state_validator);
                } else{
                    line = validatorTransition(line);
                }



                if(line == null){
                    System.out.println("Not validated in parsing process on line: "+tokens.get(i).pos[0]);
                    return null;
                }else{
                    if(begin_state_validator && !final_state_validator) {
                        begin = line.get(0);
                    } else if(final_state_validator) {
                        for (String item : line) {
                            final_states.add(item);
                        }
                    }else{
                        String from = line.get(0);
                        Character character = line.get(1).charAt(0);
                        Set<String> to = new HashSet<>();
                        line.remove(0);
                        line.remove(0);
                        for (String item : line) {
                            to.add(item);
                        }


                        //CREATING AST
                        alphabet.add(character);
                        states.add(from);
                        if(transitions.containsKey(from)){
                            transitions.get(from).put(character, to);
                        }else{
                            Map<Character, Set<String>> to_transition = new HashMap<>();
                            to_transition.put(character, to);
                            transitions.put(from, to_transition);
                        }
                    }
                }

                line.clear();
            }
            else{
                line.add(tokens.get(i).val);
            }
        }

        AutomataAST automataAST = new AutomataAST(alphabet, states, transitions, final_states, begin);

        return automataAST;
    }


    private List<String> validatorTransition(List<String> line){
        if(line.size() < 5){
            return null;
        }

        for (int i = 0; i < line.size(); i++) {
            if(line.get(i).length() == 0){
                return null;
            }
        }


        for (int i = 0; i < line.size(); i++) {
            String item = line.get(i);
            if(i % 2 == 0){
                if(i == 2  && item.length() > 1) return null;
                if(i != 2 && item.charAt(0) != 'q') return null;
            }else{
                if(i == 3 && item.charAt(0) != '>') return null;
                if(i != 3 && item.charAt(0) != ',') return null;
            }
        }

        for (int i = 0; i < line.size(); i++) {
            if(line.get(i).charAt(0) == ',' || line.get(i).charAt(0) == '>') line.remove(i);
        }
        return line;
    }
    private List<String> validateRules(List<String> line, boolean final_state_validator){

        if(line.size() < 3){
            return null;
        }

        for (int i = 0; i < line.size(); i++) {
            if(line.get(i).length() == 0){
                return null;
            }
        }

        if(!line.get(1).equals("=")) return null;
        // BEGIN
        if(!final_state_validator){
            if(!line.get(0).equals("begin")) return null;
            line.remove(0);
            line.remove(0);

            if(line.size() != 1) return  null;
        }else{//FINAL
            if(!line.get(0).equals("final")) return null;
            line.remove(0);
            line.remove(0);

            for (int i = 0; i < line.size(); i++) {
                if(line.get(i).charAt(0) == ',') line.remove(i);
            }
        }
        return line;
    }

}
