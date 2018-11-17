package parsing;

import AST.RegularExpression;
import AST.RegularExpressions.*;
import util.Token;

import java.util.ArrayList;
import java.util.List;

public class RegExpParser{
    List<Token> tokens;
    RegularExpression regExp;
    public RegExpParser(List<Token> tokens) {
        this.tokens = tokens;
    }



    public RegularExpression parseRegExp()throws Exception{
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

    private RegularExpression parseConcatOrSelectionRegExp() throws Exception{
        tokens.remove(0); //deleting (
        try {
            RegularExpression left = parseRegExp();
            tokens.remove(0); // delete left part

            Token token = tokens.get(0); //taking a separator
            String op = "";

            if(token.val.equals(".")){
                op = "con";
            }else if(token.val.equals("|")){
                op = "sel";
            }else if(token.val.equals("*")){
                tokens.remove(0);
                return  parseKleeneStar(left);
            }

            tokens.remove(0); // remove separator
            RegularExpression right = parseRegExp();
            tokens.remove(0); // remove right parenthesis

            if(op.equals("con")) {
                return parseConcatRegExp(left, right);
            }
            else if (op.equals("sel")) {
                return parseSelectionRegExp(left, right);
            }
        }catch (Exception e){
            System.out.println("Error on token: " + tokens.get(0).val + " position: "+ tokens.get(0).pos);
        }


        return null;

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

}
