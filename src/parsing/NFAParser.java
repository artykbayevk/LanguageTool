package parsing;

import AST.AutomataAST;
import util.Token;

import java.util.List;

public class NFAParser extends AutomataParser {

    public NFAParser(List<Token> tokens) {
        super(tokens);
    }

    @Override
    public AutomataAST getAutomataAST() {
        return null;
    }

}
