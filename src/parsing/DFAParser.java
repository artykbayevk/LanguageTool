package parsing;

import AST.AutomataAST;
import util.Token;

import java.util.List;

public class DFAParser extends AutomataParser {
    public DFAParser(List<Token> tokens){
        super(tokens);
    }

    @Override
    public AutomataAST getAutomataAST(){
        return null;
    }
}
