import lexing.*;
import util.Token;

import java.io.IOException;
import java.util.List;

public class LanguageTool {
    private String path;
    private String type;
    private String out_dir;
    private List<Token> tokens;
    private Lexer lexer;

    public LanguageTool(String path, String type, String output_directory){
        this.path = path;
        this.type = type;
        this.out_dir = output_directory;

        if(type.equals("DFA")) {
            lexer = new DFALexer(path);
        }else if(type.equals("NFA")){
            lexer = new NFALexer(path);
        }else if(type.equals("REG")){
            lexer = new REGLexer(path);
        }

        try{
            assert lexer != null;
            tokens = lexer.getTokens();
            if(tokens != null){
                PrintTokens(tokens);

                // TODO: 11/9/18 write a parser for parsing from tokens and check validation of structure
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
