package lexing.DFA;

import lexing.Lexer;
import lexing.tokenizer.Token;
import lexing.tokenizer.Tokenizer;

import java.util.List;

public class DFALexer extends Lexer {

    private List<Token> tokenList;


    public DFALexer(String path){
        super(path);
        Tokenizer tokenizer = new DFATokenizer(LexerCharList);
        tokenList = tokenizer.getTokens();
    }


    public List<Token> getTokens(){
        return tokenList;
    }


}
