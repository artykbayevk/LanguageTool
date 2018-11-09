package lexing;

import util.Token;

import java.io.IOException;
import java.util.List;

public class NFALexer extends Lexer {

    // TODO: 11/9/18 write a lexer for NFA
    public NFALexer(String path){
        super(path);
        System.out.println(file.toString());
    }

    public List<Token> getTokens() throws IOException {
        return null;
    }
}
