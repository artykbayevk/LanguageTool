package lexing;

import util.Token;

import java.io.IOException;
import java.util.List;

public class REGLexer extends Lexer {

    // TODO: 11/9/18 Write a lexer for regular expressions
    public REGLexer(String path){
        super(path);
        System.out.println(file.toString());
    }

    public List<Token> getTokens() throws IOException{
        return null;
    }
}
