package parsing;

import AST.AutomataAST;
import util.Token;

import java.util.List;

public abstract class AutomataParser {

    public AutomataParser(List<Token> tokens) {
    }


    public abstract AutomataAST getAutomataAST();

}
