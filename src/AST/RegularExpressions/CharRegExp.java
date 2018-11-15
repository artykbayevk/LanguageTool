package AST.RegularExpressions;

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
}
