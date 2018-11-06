package lexing;

import javafx.util.Pair;
import lexing.tokenizer.Token;

import java.util.List;

public abstract class Lexer {

    protected List<Pair<Integer,Character>> LexerCharList;

    public Lexer(String path){
        // String path of the file

        // create LexerCharList
        //        this.lexer = read from txt and bla bla bla
    }

    // TODO: 11/6/18 write function which will be return tokens for next Parsing operation
    //    getTokens


    public abstract List<Token> getTokens();

}
