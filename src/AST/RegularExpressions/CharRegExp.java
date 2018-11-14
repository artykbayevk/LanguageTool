package AST.RegularExpressions;

import AST.RegularExpression;


public class CharRegExp implements RegularExpression {
    Character character;

    public CharRegExp(Character character) {
        this.character = character;
        System.out.println("This character in Regular Expression: " + character);
    }
}
