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

    private AutomataParser automataParser;
    private RegExpParser regExpParser;
    private boolean debug;

    private AutomataAST DFA;
    private AutomataAST NFA;
    private RegularExpression REGEXP;

    private SemanticAnalyzer semanticAnalyzer;


    //CONSTRUCTOR
    public LanguageTool(String path, String type, String output_directory, boolean debug){
        this.out_dir = output_directory;
        this.type = type;
        this.debug = debug;

        if(debug)System.out.println("Debug mode on!\n");


        //creating lexer object for lexing from file to tokens
        if(type.equals("DFA") || type.equals("NFA")) {
            lexer = new AutomataLexer(path);
        }else{
            lexer = new RegExpLexer(path);
        }


        try {
            // creating tokens from lexer
            tokens = lexer.getTokens();

            // validate tokens
            if (tokens != null || tokens.size() !=0){

                // if debug == true, print all lexed tokens
                if(debug)printTokens(tokens);

                //check type of pattern
                if(type.equals("DFA") || type.equals("NFA")){



                    //if automata parse like that
                    automataParser = new AutomataParser(tokens);
                    if(type.equals("DFA")) this.DFA = automataParser.getAutomataAST();
                    else if(type.equals("NFA")) this.NFA = automataParser.getAutomataAST();

                }else if(type.equals("REG")){

                    //else if regular expression parse like that
                    regExpParser = new RegExpParser(tokens);
                    this.REGEXP= regExpParser.parseRegExp();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //setter for DFA
    private void setDFA(AutomataAST DFA) {
        this.DFA = DFA;
    }

    //setter for NFA
    public void setNFA(AutomataAST NFA) {
        this.NFA = NFA;
    }

    //setter for RegularExpression
    public void setREGEXP(RegularExpression REGEXP) {
        this.REGEXP = REGEXP;
    }

    //getter for DFA
    public AutomataAST getDFA() {
        return DFA;
    }

    //getter for NFA
    public AutomataAST getNFA() {
        return NFA;
    }

    //getter for RegularExpression
    public RegularExpression getREGEXP() {
        return REGEXP;
    }



    //tool functions

    //private function for printing all lexed tokens
    private void printTokens(List<Token> tokens){
        for (Token token : tokens) {
            System.out.println("Token: " + token.val + " on " +
                    "positions:(" + token.pos[0] + ':' + token.pos[1] + ");");
        }

        System.out.println();
    }

    //function for checking validation of pattern(NFA, DFA or RexExp)
    void checkValidation() throws Exception {
        System.out.println();
        System.out.println("--- Run semantic analyzer on pattern: ---");
        switch (type){
            case "DFA": semanticAnalyzer = new SemanticAnalyzerDFA(this.DFA);break;
            case "NFA": semanticAnalyzer = new SemanticAnalyzerNFA(this.NFA);break;
            case "REG": semanticAnalyzer = new SemanticAnalyzerRegExp(this.REGEXP);break;
        }

        semanticAnalyzer.validator();
    }

    //executing word on current DFA
    void executeDFA(String word) throws Exception{
        this.DFA.execute(word);
    }


    //converter functions

    //converting Regular Expression to NFA
    void fromRegExpToNFA(){
        this.NFA = this.REGEXP.convertToNFA();
    }

    //converting from NFA to DFA
    void convertNFAtoDFA() throws Exception{
        //Create $-closure
        Map<String, Set<String>> e_closure_map = new HashMap<>();
        for (Map.Entry<String, Map<Character, Set<String>>> pair :this.NFA.getTransitions().entrySet()) {
            e_closure_map.put(pair.getKey(), DFS(pair.getKey()));
        }

        //Creating main DFA table
        String start_state = String.join("_",e_closure_map.get(this.NFA.getStart()));
        System.out.println();
        System.out.println("Converting derivatives in process: ");
        System.out.println("Epsilon-closures: " + e_closure_map);
        System.out.println("New start: " + start_state);
        System.out.println();
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
                    converted.add(String.join("_", pair.getValue().get(input_char)));
                    pair.getValue().put(input_char, converted);

                }
            }

            String tmp[] = pair.getKey().split("_");

            for (int i = 0; i < tmp.length; i++) {
                for (String original_final : this.NFA.getFinal_states()) {
                    if (tmp[i].equals(original_final)) finalStates.add(pair.getKey());
                }
            }
            states.add(pair.getKey());
        }

        // creating trap state transition
        Map<Character, Set<String>> dead_states_transition = new HashMap<>();
        for (Character input:alphabet) {
            dead_states_transition.put(input, trapState);
        }

        dfa_transitions.put("qTrap", dead_states_transition);


        // creating DFA and return this
        this.DFA = new AutomataAST(alphabet, states, dfa_transitions, finalStates, start_state);
    }

    //converting from DFA to NFA
    void convertDFAtoNFA() throws Exception{
        this.NFA =  new AutomataAST(this.DFA.getAlphabet(), this.DFA.getStates(), this.DFA.getTransitions(), this.DFA.getFinal_states(), this.DFA.getStart());
    }

    void convertDFAtoRegExp() throws Exception{
        convertDFAtoNFA();
        this.REGEXP = this.NFA.convertToRegExp();
    }

    void convertNFAtoRegExp() throws Exception{
        convertNFAtoDFA();
        this.REGEXP = this.DFA.convertToRegExp();
    }

    //function for doing e-NFA transitions to DFA-transition
    private Map<String, Map< Character , Set<String>>> to_DFA_transitions(String start_state, Map<String, Map< Character , Set<String>>> dfa_transitions){
        String input_states[] = start_state.split("_");

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
            String new_state = String.join("_",pair_of_new_state.getValue());
            if(!dfa_transitions.containsKey(new_state)){
                dfa_transitions.putAll(to_DFA_transitions(new_state, dfa_transitions));
            }else{
                return dfa_transitions;
            }
        }
        return dfa_transitions;

    }

    //DFS function for searching path of epsilon transition
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
