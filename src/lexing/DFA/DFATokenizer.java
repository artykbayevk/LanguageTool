package lexing.DFA;

import javafx.util.Pair;
import lexing.tokenizer.Token;
import lexing.tokenizer.Tokenizer;

import java.util.List;

public class DFATokenizer implements Tokenizer {
    private List<Pair<Integer,Character>> LexerCharList;



    public DFATokenizer(List<Pair<Integer,Character>> LexerCharList){
        this.LexerCharList = LexerCharList;
    }
    public List<Token> getTokens(){
        return null;
    }
}
