package parsing;

import util.Token;

import java.util.List;

public class DFAParser extends Parser {

    // TODO: 11/12/18 create a DFAParser, that check only for construction.
    // Ex: q1 a -> q2. If there is no component, automatically reject
    // Parse from list of Token class to list of AutomataAST


    public DFAParser(List<Token> tokens){
        super(tokens);
    }
}
