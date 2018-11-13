import lexing.*;
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
            assert lexer != null;
            tokens = lexer.getTokens();
            if(tokens != null){
                PrintTokens(tokens);


                // TODO: 11/9/18 write a parser for parsing from tokens and check validation of structure
                if(type.equals("DFA") ){
                    //PARSE TO AUTOMATA AST - TYPE DFA
                }else if(type.equals("NFA")){
                    //PARSE TO AUTOMATA AST - TYPE NFA
                }else{
                    //PARSE TO REG EXP
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
}
