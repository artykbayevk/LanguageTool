package lexing;

import util.Token;

import java.io.IOException;
import java.util.List;

public class NFALexer extends Lexer {

    public NFALexer(String path){
        super(path);
        System.out.println(file.toString());
    }

    public List<Token> getTokens() throws IOException {
        return null;
    }
}
