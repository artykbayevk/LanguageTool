package lexing;

import java.io.IOException;
import java.util.List;

public class REGLexer extends Lexer {


    public REGLexer(String path){
        super(path);
        System.out.println(file.toString());
    }

    public List<Token> getTokens() throws IOException{
        return null;
    }
}
