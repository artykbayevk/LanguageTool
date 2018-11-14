package parsing;

import AST.RegularExpression;
import AST.RegularExpressions.CharRegExp;
import AST.RegularExpressions.ConcatRegularExpression;
import AST.RegularExpressions.EmptyRegularExpression;
import AST.RegularExpressions.SelectionRegularExpression;
import util.Token;

import java.util.ArrayList;
import java.util.List;

public class RegExpParser {
    List<Token> tokens;

    public RegExpParser(List<Token> tokens) {
        this.tokens = tokens;
        //all this things for parsing - ((a.b)|(c)|($))
        RegularExpression a = new CharRegExp('a');
        RegularExpression b = new CharRegExp('b');
        List<RegularExpression> concats = new ArrayList<>();
        concats.add(a);
        concats.add(b);

        RegularExpression concatination = new ConcatRegularExpression(concats);

        List<RegularExpression> selections = new ArrayList<>();
        selections.add(concatination);
        selections.add(new CharRegExp('c'));
        selections.add(new EmptyRegularExpression());

        RegularExpression regexp = new SelectionRegularExpression(selections);


    }

    public RegularExpression regExpAST(){
        return null;
    }
}
