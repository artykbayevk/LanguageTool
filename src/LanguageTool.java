import lexing.*;
import util.Token;

import java.io.IOException;
import java.util.List;

public class LanguageTool {
    public String path;
    public String type;
    public String out_dir;

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
            List<Token> tokens = lexer.getTokens();
            if(tokens != null){
                PrintTokens(tokens);
                //parsing operation

            }
        }catch (IOException e){}

        // TODO: 11/7/18 this token will be use for parsing
    }
    private void PrintTokens(List<Token> tokens){
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println("Token: "+tokens.get(i).val + " on " +
                    "positions:("+tokens.get(i).pos[0]+':'+tokens.get(i).pos[1]+");");
        }
    }
}
