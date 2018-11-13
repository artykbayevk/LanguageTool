import AST.AutomataAST;
import lexing.*;
import parsing.AutomataParser;
import parsing.RegExpParser;
import util.Token;

import java.io.IOException;
import java.util.List;

public class LanguageTool {
    private String out_dir;
    private List<Token> tokens;
    private Lexer lexer;

    public LanguageTool(String path, String type, String output_directory){
        this.out_dir = output_directory;

        if(type.equals("DFA") || type.equals("NFA")) {
            lexer = new AutomataLexer(path);
        }else{
            lexer = new RegExpLexer(path);
        }


        try{
            tokens = lexer.getTokens();
            System.out.println(tokens.size());
            if(tokens.size() != 0){
                PrintTokens(tokens);
                if(type.equals("DFA") || type.equals("NFA")){
                    AutomataParser automataParser = new AutomataParser(tokens);
                    AutomataAST AST = automataParser.getAutomataAST();
                    if(AST != null){
                        PrintAutomataAST(AST);
                    }
                }else{
                    // TODO PARSE TO REG EXP
                    RegExpParser reg_parser = new RegExpParser(tokens);
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
}
