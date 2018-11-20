import AST.AutomataAST;
import AST.RegularExpression;
import lexing.*;
import parsing.AutomataParser;
import parsing.RegExpParser;
import semantic.SemanticAnalyzer;
import semantic.SemanticAnalyzerDFA;
import semantic.SemanticAnalyzerNFA;
import semantic.SemanticAnalyzerRegExp;
import util.Token;

import java.io.IOException;
import java.util.List;

public class LanguageTool {
    private String out_dir;
    private List<Token> tokens;
    private Lexer lexer;
    private String type;


    private AutomataAST DFA;
    private AutomataAST NFA;
    private RegularExpression REGEXP;


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
                        if(AST != null) System.out.println("Lexed and Parsed DFA/NFA");
//                            PrintAutomataAST(AST);
                        if(type.equals("DFA")) this.DFA = AST;
                        if(type.equals("NFA")) this.NFA = AST;

                    }else{

                        RegExpParser reg_parser = new RegExpParser(tokens);
                        try{
                            RegularExpression regExpAST = reg_parser.parseRegExp();
                            this.REGEXP = regExpAST;


//                            System.out.println(regExpAST.toString());
//                            regExpAST.printElements();
                        }catch (Exception e){
                            System.out.println("Exception in parsing regular expression!");
                        }
                    }
                }
            }

        }catch (IOException e){
            System.out.println("Problems in Lexing");
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



    public AutomataAST getDFA() {
        return this.DFA;
    }

    public AutomataAST getNFA() {
        return this.NFA;
    }

    public RegularExpression getREGEXP() {
        return this.REGEXP;
    }


    public boolean checkValidation() throws Exception {
        if(type.equals("DFA")){
            SemanticAnalyzer semDFA = new SemanticAnalyzerDFA(this.DFA);
            return semDFA.validator();
        }else if(type.equals("NFA")){
            SemanticAnalyzer semNFA = new SemanticAnalyzerNFA(this.NFA);
            return semNFA.validator();
        }else if(type.equals("REG")){
            SemanticAnalyzer semREG = new SemanticAnalyzerRegExp(this.REGEXP);
            return semREG.validator();
        }
        return false;
    }
    // TODO: 11/15/18 Write a semantic analyzer for NFA,DFA and RegExp 


    // TODO: 11/15/18 Write a executor for DFA 


    // TODO: 11/15/18 Write a converter - plak plak 

}
