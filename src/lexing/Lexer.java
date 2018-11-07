package lexing;

import util.Token;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Lexer {

    protected List<Token> LexerCharList;
    protected File file;

    public Lexer(String path){
        this.file = new File(path);
    }

    public abstract List<Token> getTokens() throws IOException;
}
