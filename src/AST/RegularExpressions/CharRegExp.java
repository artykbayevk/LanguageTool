package AST.RegularExpressions;

import AST.AutomataAST;
import AST.RegularExpression;


public class CharRegExp implements RegularExpression {
    Character character;

    public CharRegExp(Character character) {
        this.character = character;
    }

    @Override
    public void printElements() {
        System.out.print(character);
    }

    @Override
    public String value() {
        return character.toString();
    }


    @Override
    public RegularExpression itself() {
        return this;
    }


    @Override
    public AutomataAST convertToNFA() {
        return null;
    }
}
