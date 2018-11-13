package parsing;

import AST.AutomataAST;
import util.Token;

import java.util.List;

public abstract class AutomataParser {

    List<Token> tokens;

    public AutomataParser(List<Token> tokens) {
        this.tokens = tokens;
    }


    public abstract AutomataAST getAutomataAST();

}
