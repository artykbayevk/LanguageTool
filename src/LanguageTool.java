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

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        DFA.execute(type, word);
    }

    AutomataAST convertNFAtoDFA() throws Exception{
        this.NFA.getAlphabet().remove('$');
        Set<Character> alphabet =this.NFA.getAlphabet();

        for (Map.Entry<String, Map<Character, Set<String> >> pair:NFA.getTransitions().entrySet()) {
            System.out.println(pair);
        }

        return this.NFA;
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
}
