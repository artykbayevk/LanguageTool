import AST.AutomataAST;
import AST.RegularExpression;
import lexing.AutomataLexer;
import lexing.Lexer;
import lexing.RegExpLexer;
import parsing.AutomataParser;
import parsing.RegExpParser;
import semantic.SemanticAnalyzer;
import semantic.SemanticAnalyzerDFA;
import semantic.SemanticAnalyzerNFA;
import semantic.SemanticAnalyzerRegExp;
import util.Token;

import java.util.*;

public class LanguageTool {
    private String out_dir;
    private String type;

    private List<Token> tokens;
    private Lexer lexer;

    private AutomataAST DFA;
    private AutomataAST NFA;
    private RegularExpression REGEXP;

    private SemanticAnalyzer semanticAnalyzer;

    public LanguageTool(String path, String type, String output_directory){
        this.out_dir = output_directory;
        this.type = type;


        if(type.equals("DFA") || type.equals("NFA")) {
            lexer = new AutomataLexer(path);
        }else{
            lexer = new RegExpLexer(path);
        }

        try{
            tokens = lexer.getTokens();
            if (tokens != null){
                if(tokens.size() != 0){
                    if(type.equals("DFA") || type.equals("NFA")){
                        AutomataParser automataParser = new AutomataParser(tokens);
                        AutomataAST AST = automataParser.getAutomataAST();
                        System.out.println("----");
                        System.out.println("Lexed and Parsed "+type);
                        PrintAutomataAST(AST);
                        System.out.println("----");

                        if(type.equals("DFA")) this.DFA = AST;
                        if(type.equals("NFA")) this.NFA = AST;

                    }else{

                        RegExpParser reg_parser = new RegExpParser(tokens);
                        try{
                            RegularExpression regExpAST = reg_parser.parseRegExp();
                            System.out.println("Lexed and Parsed regular expression");
                            this.REGEXP = regExpAST;
                        }catch (Exception e){
                            throw new Exception("Exception in parsing regular expression!");
                        }
                    }
                }else{
                    throw new Exception("Tokens is empty");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void PrintTokens(List<Token> tokens){
        for (Token token : tokens) {
            System.out.println("Token: " + token.val + " on " +
                    "positions:(" + token.pos[0] + ':' + token.pos[1] + ");");
        }
    }

    private void PrintAutomataAST(AutomataAST ast){
        System.out.println("Start state "+ ast.getStart());
        System.out.println("Final states "+ ast.getFinal_states().toString());

        System.out.println("Transitions " + ast.getTransitions().toString());
        System.out.println("Alphabet " + ast.getAlphabet().toString());
        System.out.println("States " + ast.getStates().toString());
    }

    void checkValidation() throws Exception {
        switch (type){
            case "DFA": semanticAnalyzer = new SemanticAnalyzerDFA(this.DFA);break;
            case "NFA": semanticAnalyzer = new SemanticAnalyzerNFA(this.NFA);break;
            case "REG": semanticAnalyzer = new SemanticAnalyzerRegExp(this.REGEXP);break;
        }

        semanticAnalyzer.validator();
    }

    void executeDFA(String word) throws Exception{
        this.DFA.execute(type, word);
    }

    public void setDFA(AutomataAST DFA) {
        this.DFA = DFA;
    }

    public void setNFA(AutomataAST NFA) {
        this.NFA = NFA;
    }

    public void setREGEXP(RegularExpression REGEXP) {
        this.REGEXP = REGEXP;
    }

    public void printRegExp(){
        this.REGEXP.printElements();
    }

    public void changeType(String type){
        this.type = type;
    }

    AutomataAST convertNFAtoDFA() throws Exception{
        //Create $-closure
        Map<String, Set<String>> e_closure_map = new HashMap<>();
        for (Map.Entry<String, Map<Character, Set<String>>> pair :this.NFA.getTransitions().entrySet()) {
            e_closure_map.put(pair.getKey(), DFS(pair.getKey()));
        }

        //Creating main DFA table
        String start_state = String.join(",",e_closure_map.get(this.NFA.getStart()));
        System.out.println("E-closures: " + e_closure_map);
        System.out.println("New start: " + start_state + "\n----");

        // delete empty char and craete DFA_transitions table
        this.NFA.getAlphabet().remove('$');
        Map<String, Map< Character , Set<String>>> dfa_transitions = new HashMap<>();


        // recursively create DFA transitions table
        dfa_transitions = to_DFA_transitions(start_state, dfa_transitions);

        // create qTrap for completing transition table and create alphabet set
        Set<Character> alphabet = new HashSet<>();
        for (Map.Entry<String, Map<Character, Set<String>>> pair :dfa_transitions.entrySet()) {
            alphabet.addAll(pair.getValue().keySet());
        }


        // creating trapState and allStates
        Set<String> trapState = new HashSet<>();
        Set<String> states = new HashSet<>();
        Set<String> finalStates = new HashSet<>();
        trapState.add("qTrap");
        states.add("qTrap");


        // declaring and assigning trapState, states and final states
        for (Map.Entry<String, Map<Character, Set<String>>> pair :dfa_transitions.entrySet()) {
            for (Character input_char : alphabet) {
                if (!pair.getValue().containsKey(input_char)) {
                    pair.getValue().put(input_char, trapState);
                } else {
                    Set<String> converted = new HashSet<>();
                    converted.add(String.join(",", pair.getValue().get(input_char)));
                    pair.getValue().put(input_char, converted);

                }
            }

            String tmp[] = pair.getKey().split(",");

            for (int i = 0; i < tmp.length; i++) {
                for (String original_final : this.NFA.getFinal_states()) {
                    if (tmp[i].equals(original_final)) finalStates.add(pair.getKey());
                }
            }
            states.add(pair.getKey());
        }

        // creating DFA and return this
        return new AutomataAST(alphabet, states, dfa_transitions, finalStates, start_state);
    }

    private Map<String, Map< Character , Set<String>>> to_DFA_transitions(String start_state, Map<String, Map< Character , Set<String>>> dfa_transitions){
        String input_states[] = start_state.split(",");

        Map<Character, Set<String>> main_transition = new HashMap<>();

        //Let's check new created state on the inputs
        for (Character input_char: this.NFA.getAlphabet()) {
            Set<String> out_states = new HashSet<>();
            for (String input_state : input_states) {
                if (this.NFA.getTransitions().containsKey(input_state)) {
                    Map<Character, Set<String>> find_transition = this.NFA.getTransitions().get(input_state);
                    if (find_transition.containsKey(input_char)) {
                        out_states.addAll(find_transition.get(input_char));
                    }else{
                        continue;
                    }
                } else {
                    continue;
                }
            }



            // check our out_states
            if(out_states.size() != 0) {
                Set<String> res_states = new HashSet<>();
                for (String next : out_states) {
                    res_states.addAll(DFS(next));
                }
                main_transition.put(input_char, res_states);
            }
        }

        dfa_transitions.put(start_state, main_transition);


        for (Map.Entry<Character, Set<String>> pair_of_new_state:main_transition.entrySet()) {
            String new_state = String.join(",",pair_of_new_state.getValue());
            if(!dfa_transitions.containsKey(new_state)){
                dfa_transitions.putAll(to_DFA_transitions(new_state, dfa_transitions));
            }else{
                return dfa_transitions;
            }
        }
        return dfa_transitions;

    }

    private Set<String> DFS(String node){
        Set<String> res = new HashSet<>();
        res.add(node);
        Map<Character, Set<String>> transitions = this.NFA.getTransitions().get(node);

        if(transitions == null) return res;

        for (Map.Entry<Character, Set<String>> pair:transitions.entrySet()) {
            if(pair.getKey().equals('$')){
                for (String out_state:pair.getValue()) {
                    res.addAll(DFS(out_state));
                }
            }
        }
        return res;
    }

}
