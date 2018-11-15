package parsing;

import AST.RegularExpression;
import AST.RegularExpressions.*;
import util.Token;

import java.util.ArrayList;
import java.util.List;

public class RegExpParser {
    List<Token> tokens;
    RegularExpression regExp;
    public RegExpParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // TODO: 11/15/18 Write a parser with recursive descent, which will be return RegExpAST 
    public RegularExpression parseRegExp(){
        // MY REGULAR EXPRESSION  - (a|b)
        // NEXT REGULAR EXPRESSION  - (a.b)

        Token current = tokens.get(0);

        if(current.val.equals("(")){
            regExp  = parseConcatOrSelectionRegExp();
        }else if(current.val.equals("$")){
            regExp = parseEmptyRegExp();
        }else{
            regExp = parseLiteral(current.val.charAt(0));
        }

        return regExp;
    }

    private RegularExpression parseConcatOrSelectionRegExp(){
        tokens.remove(0); //deleting (
        RegularExpression left = parseRegExp();
        tokens.remove(0);
        Token token = tokens.get(0);
        System.out.println(token.val);
        String op = "";
        if(token.val.equals(".")){
            op = "con";
        }else{
            op = "sel";
        }

        tokens.remove(0);
        RegularExpression right = parseRegExp();
        tokens.remove(0);

        if(op.equals("con")) return new ConcatRegularExpression(left, right);
        else if (op.equals("sel")) return new SelectionRegularExpression(left, right);
        else return null;
    }

    private RegularExpression parseConcatRegExp(RegularExpression left, RegularExpression right){
        return new ConcatRegularExpression(left, right);
    }

    private RegularExpression parseSelectionRegExp(RegularExpression left, RegularExpression right){
        return new SelectionRegularExpression(left, right);
    }

    private RegularExpression parseLiteral(Character inputChar){
        return new CharRegExp(inputChar);
    }

    private RegularExpression parseKleeneStar(RegularExpression inputRegularExpression){
        return new KleeneStarRegularExpression(inputRegularExpression);
    }

    private RegularExpression parseEmptyRegExp(){
        return new EmptyRegularExpression();
    }


    // TODO: 11/15/18 Create a function for creating a Tree
}
